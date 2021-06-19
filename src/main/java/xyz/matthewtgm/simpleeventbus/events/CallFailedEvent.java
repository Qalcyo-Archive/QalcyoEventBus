package xyz.matthewtgm.simpleeventbus.events;

import xyz.matthewtgm.simpleeventbus.Event;

public class CallFailedEvent extends Event {
    public final Event event;
    public final Class<? extends Event> eventClazz;
    public final int callAttempts;
    public CallFailedEvent(Event event, Class<? extends Event> eventClazz, int callAttempts) {
        this.event = event;
        this.eventClazz = eventClazz;
        this.callAttempts = callAttempts;
    }
}