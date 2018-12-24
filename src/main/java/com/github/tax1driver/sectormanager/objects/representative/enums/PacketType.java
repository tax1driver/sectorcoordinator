package com.github.tax1driver.sectormanager.objects.representative.enums;


import com.github.tax1driver.sectormanager.objects.representative.packets.AuthPacket;
import com.github.tax1driver.sectormanager.objects.representative.packets.GenericPacket;

public enum PacketType{
    AUTH(AuthPacket.class, 0);


    private final Class<? extends GenericPacket> clazz;
    private final int id;
    PacketType(Class<? extends GenericPacket> clazz, int id) {
        this.clazz = clazz;
        this.id = id;
    }

    public Class<? extends GenericPacket> getValue() {
        return clazz;
    }

    public static PacketType forName(String name) {
        for (PacketType type : values()) {
            if (type.name().equalsIgnoreCase(name))
                return type;
        }
        return null;
    }

    public static PacketType forClass(Class<? extends GenericPacket> packetClass) {
        for (PacketType type : values()) {
            if (type.getValue().equals(packetClass))
                return type;

        }
        return null;
    }

    public static PacketType forID(int id) {
        for (PacketType type : values())  {
            if (type.id == id)
                return type;
        }
        return null;
    }
}
