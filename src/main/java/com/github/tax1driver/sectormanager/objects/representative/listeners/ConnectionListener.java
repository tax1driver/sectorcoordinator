package com.github.tax1driver.sectormanager.objects.representative.listeners;

import com.github.tax1driver.sectormanager.helpers.EventManager;
import com.github.tax1driver.sectormanager.objects.networking.events.ConnectionIncomingDataEvent;
import com.github.tax1driver.sectormanager.objects.prototyping.events.EventHandler;
import com.github.tax1driver.sectormanager.objects.prototyping.events.Listener;
import com.github.tax1driver.sectormanager.objects.representative.Client;
import com.github.tax1driver.sectormanager.objects.representative.helpers.PacketDeserializer;
import com.github.tax1driver.sectormanager.objects.representative.enums.PacketType;
import com.github.tax1driver.sectormanager.objects.representative.SectorCommunicationPacket;
import com.github.tax1driver.sectormanager.objects.representative.packets.AuthPacket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;

public class ConnectionListener implements Listener {
    private Client client;

    public ConnectionListener(Client client) {
        this.client = client;
        EventManager.getInstance().addEventListener(this);
    }

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(SectorCommunicationPacket.class, new PacketDeserializer())
            .create();

    @EventHandler
    public void onDataReceived(ConnectionIncomingDataEvent e) {
        String stringRepresentation = new String(e.data(), StandardCharsets.UTF_8);
        SectorCommunicationPacket packet = gson.fromJson(stringRepresentation, SectorCommunicationPacket.class);

        if (packet.type() == PacketType.AUTH) {
            AuthPacket data = (AuthPacket) packet.data();


        }
    }
}
