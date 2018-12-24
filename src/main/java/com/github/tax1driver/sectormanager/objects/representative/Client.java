package com.github.tax1driver.sectormanager.objects.representative;

import com.github.tax1driver.sectormanager.helpers.ClientManager;
import com.github.tax1driver.sectormanager.helpers.GsonHelper;
import com.github.tax1driver.sectormanager.objects.networking.Connection;
import com.github.tax1driver.sectormanager.objects.prototyping.Listener;
import com.github.tax1driver.sectormanager.objects.representative.enums.*;
import com.github.tax1driver.sectormanager.objects.representative.helpers.PacketDeserializer;
import com.github.tax1driver.sectormanager.objects.representative.packets.AuthPacket;
import com.github.tax1driver.sectormanager.objects.representative.packets.DisconnectPacket;
import com.github.tax1driver.sectormanager.objects.representative.packets.GenericPacket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ThreadLocalRandom;

/*
 * I decided to stop at this level of abstraction
 * yes, both network related and high level methods are here.
 */
public class Client implements Listener
{
    private Connection connection;
    private ClientAuthority authority;

    public Client(Connection conn) {
        this.connection = conn;
        this.authority = new ClientAuthority();

        this.greet();
    }

    private void greet() {
        byte[] challenge = new byte[ClientAuthority.CHALLENGE_SIZE];
        ThreadLocalRandom.current().nextBytes(challenge);

        AuthPacket p = new AuthPacket(challenge, null);
        this.sendPacket(p);

        this.getAuthority().setState(AuthenticationState.AWAITING_CHALLENGE_RESPONSE);
    }

    public void kick(KickReason reason) {
        DisconnectPacket p = new DisconnectPacket();
        p.setInitiator(ConnectionSide.SERVER);
        p.setReason(DisconnectReason.KICK);
        p.setKickReason(reason);

        this.sendPacket(p);
        this.drop();
    }

    public void kick(String reason) {
        DisconnectPacket p = new DisconnectPacket();
        p.setInitiator(ConnectionSide.SERVER);
        p.setReason(DisconnectReason.KICK);
        p.setKickReason(KickReason.UNKNOWN);
        p.setKickMessage(reason);

        this.sendPacket(p);
        this.drop();
    }

    public void disconnect() {
        DisconnectPacket p = new DisconnectPacket();
        p.setInitiator(ConnectionSide.SERVER);
        p.setReason(DisconnectReason.UNKNOWN);

        this.sendPacket(p);
        this.drop();
    }

    public void drop() {
        this.connection.disconnect();
        ClientManager.getClients().remove(this);
    }

    public ClientAuthority getAuthority() {
        return authority;
    }

    public Connection getConnection() {
        return connection;
    }

    private void sendPacket(GenericPacket data) {
        PacketType type = PacketType.forClass(data.getClass());
        SectorCommunicationPacket packet = new SectorCommunicationPacket(type, data);

        this.sendComPacket(packet);
    }

    private void sendComPacket(SectorCommunicationPacket packet) {
        String stringRepresentation = GsonHelper.gson().toJson(packet, SectorCommunicationPacket.class);
        byte[] buf;
        try {
            buf = stringRepresentation.getBytes("UTF-8");
            connection.send(buf);
        } catch(UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }
}
