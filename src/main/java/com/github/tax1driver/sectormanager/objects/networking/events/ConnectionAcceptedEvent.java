package com.github.tax1driver.sectormanager.objects.networking.events;

import com.github.tax1driver.sectormanager.objects.networking.Connection;
import com.github.tax1driver.sectormanager.objects.prototyping.Event;

public class ConnectionAcceptedEvent extends Event {
    private Connection connection;

    public ConnectionAcceptedEvent(Connection conn) {
        this.connection = conn;
    }

    public Connection getConnection() {
        return connection;
    }
}
