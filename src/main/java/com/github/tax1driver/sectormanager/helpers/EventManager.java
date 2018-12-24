package com.github.tax1driver.sectormanager.helpers;

import com.github.tax1driver.sectormanager.objects.prototyping.Event;
import com.github.tax1driver.sectormanager.objects.prototyping.EventHandler;
import com.github.tax1driver.sectormanager.objects.prototyping.Listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventManager {
    private HashMap<Listener, List<Method>> handlers;

    private EventManager() {
        this.handlers = new HashMap<>();
    }

    public void callEventListeners(Event event, Class<? extends Event> eventType) throws IllegalArgumentException {
        for (Listener listener : handlers.keySet()) {
            for (Method method : handlers.get(listener)) {
                if (!method.getParameters()[0].getType().equals(eventType))
                    throw new IllegalArgumentException("invalid event type");

                try {
                    method.invoke(listener, event);
                } catch(IllegalAccessException|InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public EventManager addEventListener(Listener listener) {
        handlers.putIfAbsent(listener, new ArrayList<>());
        importHandlersFrom(listener);
        return this;
    }

    private void importHandlersFrom(Listener listener) {
        for (Method method : listener.getClass().getMethods()) {
            if (method.isAnnotationPresent(EventHandler.class)) {
                if (method.getParameterCount() == 1 && method.getParameters()[0].getType().isAssignableFrom(Event.class)) {
                    handlers.get(listener).add(method);
                }
            }
        }
    }

    private static EventManager instance;
    public static EventManager getInstance() {
        if (instance == null)
            instance = new EventManager();

        return instance;
    }
}
