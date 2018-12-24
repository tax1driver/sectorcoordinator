package com.github.tax1driver.sectormanager.objects.prototyping.events;

public class Event {
    private boolean cancelled;
    private Class<?> invoker;

    public Event(Class<?> invoker) {
        this.cancelled = false;
        this.invoker = invoker;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Class<?> getInvoker() {
        return invoker;
    }
}
