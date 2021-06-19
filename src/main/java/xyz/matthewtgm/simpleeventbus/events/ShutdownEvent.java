package xyz.matthewtgm.simpleeventbus.events;

import xyz.matthewtgm.simpleeventbus.Event;

/**
 * Called when the JVM is shut down.
 * @author MatthewTGM
 * @since 1.0
 */
public class ShutdownEvent extends Event {
    public final Runnable hook;
    public ShutdownEvent(Runnable hook) {
        this.hook = hook;
    }
}