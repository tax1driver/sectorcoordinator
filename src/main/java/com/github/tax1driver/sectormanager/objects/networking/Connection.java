package com.github.tax1driver.sectormanager.objects.networking;

import com.github.tax1driver.sectormanager.helpers.EventManager;
import com.github.tax1driver.sectormanager.objects.prototyping.events.Event;
import com.github.tax1driver.sectormanager.objects.prototyping.events.EventHandler;
import com.github.tax1driver.sectormanager.objects.prototyping.events.Listener;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;

public class Connection {
    private SocketChannel channel;
    private Server server;
    private ByteBuffer channelBuffer;


    public Connection(SocketChannel channel, Server server) {
        this.channel = channel;
        this.server = server;

        this.channelBuffer = ByteBuffer.allocate(4 * 1024);
    }



    public void send(byte[] buffer) {
        try {
            channelBuffer.put(buffer);
            channel.write(channelBuffer);
            channelBuffer.clear();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            channel.close();
            server.clients().remove(this);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }


    void processIncomingData(byte[] buf) {

    }

    SocketChannel channel() {
        return channel;
    }
}
