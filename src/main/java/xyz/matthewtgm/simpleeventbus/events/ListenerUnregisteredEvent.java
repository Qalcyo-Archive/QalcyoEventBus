package xyz.matthewtgm.simpleeventbus.events;

import xyz.matthewtgm.simpleeventbus.EventCancellable;

public class ListenerUnregisteredEvent extends EventCancellable {
    public final Class<?> clazz;
    public ListenerUnregisteredEvent(Class<?> clazz) {
        this.clazz = clazz;
    }
}