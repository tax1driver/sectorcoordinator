package com.github.tax1driver.sectormanager.objects.representative;


import com.github.tax1driver.sectormanager.helpers.GsonHelper;
import com.github.tax1driver.sectormanager.objects.representative.enums.PacketType;
import com.github.tax1driver.sectormanager.objects.representative.helpers.PacketDeserializer;
import com.github.tax1driver.sectormanager.objects.representative.packets.GenericPacket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class SectorCommunicationPacket {
    private PacketType packetID;
    private GenericPacket packetData;

    public SectorCommunicationPacket(PacketType packetID, GenericPacket packetData) {
        this.packetID = packetID;
        this.packetData = packetData;
    }

    public static SectorCommunicationPacket fromBuffer(ByteBuffer buffer) {
        try {
            String stringRepresentation = new String(buffer.array(), "UTF-8");
            return GsonHelper.gson().fromJson(stringRepresentation, SectorCommunicationPacket.class);
        } catch(UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public PacketType type() {
        return packetID;
    }

    public GenericPacket data() {
        return packetData;
    }


}
