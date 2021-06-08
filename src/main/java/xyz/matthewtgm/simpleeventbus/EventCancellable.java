package xyz.matthewtgm.simpleeventbus;

/**
 * Base event class that can be "cancelled".
 *
 * @author MatthewTGM
 * @since 1.0
 */
public class EventCancellable extends Event {

    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}