package com.github.tax1driver.sectormanager.objects.networking.events;

import com.github.tax1driver.sectormanager.objects.networking.Connection;
import com.github.tax1driver.sectormanager.objects.prototyping.Event;

public class ConnectionIncomingDataEvent extends Event {
    private Connection connection;
    private byte[] dataBuffer;

    public byte[] data() {
        return dataBuffer;
    }
    public Connection getSender() {
        return connection;
    }
}
