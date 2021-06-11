package xyz.matthewtgm.simpleeventbus.events;

import xyz.matthewtgm.simpleeventbus.EventCancellable;

import java.lang.reflect.Method;

/**
 * Called when an event listener is registered.
 * @author MatthewTGM
 * @since 1.3.1
 */
public class EventListenerRegisteredEvent extends EventCancellable {
    public final Class<?> clazz;
    public final Method method;
    public EventListenerRegisteredEvent(final Class<?> clazz, final Method method) {
        this.clazz = clazz;
        this.method = method;
    }
}