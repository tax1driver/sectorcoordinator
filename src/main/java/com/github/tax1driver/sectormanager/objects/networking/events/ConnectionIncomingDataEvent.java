package com.github.tax1driver.sectormanager.objects.networking.events;

import com.github.tax1driver.sectormanager.objects.prototyping.events.Event;

public class ConnectionIncomingDataEvent extends Event {
    private byte[] dataBuffer;

    public ConnectionIncomingDataEvent(Class<?> invoker) {
        super(invoker);
    }

    public byte[] data() {
        return dataBuffer;
    }
}
