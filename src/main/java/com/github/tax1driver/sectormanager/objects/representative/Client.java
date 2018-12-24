package com.github.tax1driver.sectormanager.objects.representative;

import com.github.tax1driver.sectormanager.objects.networking.Connection;
import com.github.tax1driver.sectormanager.objects.prototyping.events.Listener;
import com.github.tax1driver.sectormanager.objects.representative.enums.ConnectionSide;
import com.github.tax1driver.sectormanager.objects.representative.enums.DisconnectReason;
import com.github.tax1driver.sectormanager.objects.representative.enums.KickReason;
import com.github.tax1driver.sectormanager.objects.representative.enums.PacketType;
import com.github.tax1driver.sectormanager.objects.representative.helpers.PacketDeserializer;
import com.github.tax1driver.sectormanager.objects.representative.listeners.ConnectionListener;
import com.github.tax1driver.sectormanager.objects.representative.packets.DisconnectPacket;
import com.github.tax1driver.sectormanager.objects.representative.packets.GenericPacket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;

/*
 * I decided to stop at this level of abstraction
 * yes, both network related and high level methods are here.
 */
public class Client implements Listener
{
    private Connection connection;
    private ClientAuthority authority;

    private ConnectionListener listener;

    public Client(Connection conn) {
        this.connection = conn;
        this.authority = new ClientAuthority();
        this.listener = new ConnectionListener(this);
    }

    static Gson gson = new GsonBuilder()
            .registerTypeAdapter(SectorCommunicationPacket.class, new PacketDeserializer())
            .create();


    public void kick(KickReason reason) {
        DisconnectPacket p = new DisconnectPacket();
        p.setInitiator(ConnectionSide.SERVER);
        p.setReason(DisconnectReason.KICK);
        p.setKickReason(reason);

        this.sendPacket(p);
        this.connection.disconnect();
    }

    public void kick(String reason) {
        DisconnectPacket p = new DisconnectPacket();
        p.setInitiator(ConnectionSide.SERVER);
        p.setReason(DisconnectReason.KICK);
        p.setKickReason(KickReason.UNKNOWN);
        p.setKickMessage(reason);

        this.sendPacket(p);
        this.connection.disconnect();
    }



    public ClientAuthority getAuthority() {
        return authority;
    }

    public Connection getConnection() {
        return connection;
    }

    public void sendPacket(GenericPacket data) {
        PacketType type = PacketType.forClass(data.getClass());
        SectorCommunicationPacket packet = new SectorCommunicationPacket(type, data);

        this.sendComPacket(packet);
    }

    public void sendComPacket(SectorCommunicationPacket packet) {
        String stringRepresentation = gson.toJson(packet, SectorCommunicationPacket.class);
        byte[] buf;
        try {
            buf = stringRepresentation.getBytes("UTF-8");
            connection.send(buf);
        } catch(UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }
}
