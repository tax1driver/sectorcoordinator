package com.github.tax1driver.sectormanager.objects.representative.helpers;

import com.github.tax1driver.sectormanager.objects.representative.SectorCommunicationPacket;
import com.github.tax1driver.sectormanager.objects.representative.enums.PacketType;
import com.github.tax1driver.sectormanager.objects.representative.packets.GenericPacket;
import com.google.gson.*;

import java.lang.reflect.Type;

public class PacketDeserializer implements JsonDeserializer<SectorCommunicationPacket> {

    @Override
    public SectorCommunicationPacket deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        PacketType packetType = PacketType.forID(object.get("packetID").getAsInt());

        Class<? extends GenericPacket> clazz = packetType.getValue();
        GenericPacket packetData = jsonDeserializationContext.deserialize(object.get("packetData"), clazz);

        return new SectorCommunicationPacket(packetType, packetData);
    }
}
