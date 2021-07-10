package xyz.matthewtgm.simpleeventbus.events;

import xyz.matthewtgm.simpleeventbus.Event;
import xyz.matthewtgm.simpleeventbus.EventCancellable;

/**
 * Called when the JVM is shut down.
 * @author MatthewTGM
 * @since 1.0
 */
public class ShutdownEvent extends EventCancellable {
    public final Runnable hook;
    public ShutdownEvent(Runnable hook) {
        this.hook = hook;
    }
}