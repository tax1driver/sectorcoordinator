package com.github.tax1driver.sectormanager.objects.networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Server extends Thread {
    private ServerSocketChannel serverChannel;
    private Selector selector;
    private InetSocketAddress localAddress;
    private final ByteBuffer channelBuffer;

    private List<Connection> connections;

    public Server(InetSocketAddress local) throws IOException {
        this.localAddress = local;

        this.serverChannel = ServerSocketChannel.open();
        this.serverChannel.bind(local).configureBlocking(false);

        this.channelBuffer = ByteBuffer.allocate(4 * 1024);
        this.selector = Selector.open();


        this.serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);

        this.connections = new ArrayList<>();
    }


    @Override
    public void run() {
        while(!this.isInterrupted() && this.serverChannel.isOpen()) {
            try {
                if (selector.select() > 0) {
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    SelectionKey currentKey;

                    while (keyIterator.hasNext()) {
                        currentKey = keyIterator.next();

                        if (currentKey.isAcceptable()) {
                            SocketChannel accepted = serverChannel.accept();
                            accepted.register(selector, SelectionKey.OP_READ);
                            //TODO: raise NewConnectionEvent
                        }
                        else if (currentKey.isReadable()) {
                            SocketChannel readable = (SocketChannel) currentKey.channel();
                            int read = readable.read(channelBuffer);

                            if (read == -1) {
                                // EOS
                                readable.close();
                            }

                            for (Connection connection : connections) {
                                // find corresponding connection
                                if (connection.channel() == readable)
                                    connection.processIncomingData(channelBuffer.array());
                            }

                            channelBuffer.clear();
                        }
                    }
                }

                Thread.sleep(5);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }


    public List<Connection> clients() {
        return connections;
    }
}
