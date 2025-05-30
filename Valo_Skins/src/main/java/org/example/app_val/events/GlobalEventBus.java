package org.example.app_val.events;

import com.google.common.eventbus.EventBus;

public class GlobalEventBus {
    private static final EventBus EVENT_BUS = new EventBus();

    public static EventBus getBus() {
        return EVENT_BUS;
    }
}
