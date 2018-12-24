package com.github.tax1driver.sectormanager.helpers;

import com.github.tax1driver.sectormanager.objects.representative.SectorCommunicationPacket;
import com.github.tax1driver.sectormanager.objects.representative.helpers.PacketDeserializer;
import com.google.gson.*;
import org.bson.internal.Base64;

import java.lang.reflect.Type;

public class GsonHelper {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(SectorCommunicationPacket.class, new PacketDeserializer())
            .registerTypeAdapter(byte[].class, new ByteAdapter())
            .create();

    private static class ByteAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        @Override
        public byte[] deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return Base64.decode(jsonElement.getAsString());
        }

        @Override
        public JsonElement serialize(byte[] bytes, Type type, JsonSerializationContext jsonSerializationContext) {
            return jsonSerializationContext.serialize(Base64.encode(bytes));
        }
    }

    public static Gson gson() {
        return gson;
    }
}
