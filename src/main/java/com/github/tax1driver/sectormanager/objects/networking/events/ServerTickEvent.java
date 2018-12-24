package com.github.tax1driver.sectormanager.objects.networking.events;

import com.github.tax1driver.sectormanager.objects.prototyping.Event;

public class ServerTickEvent extends Event {
    private long deltaTime;

    public ServerTickEvent(long deltaTime) {
        this.deltaTime = deltaTime;
    }

    public long getDeltaTime() {
        return deltaTime;
    }
}
