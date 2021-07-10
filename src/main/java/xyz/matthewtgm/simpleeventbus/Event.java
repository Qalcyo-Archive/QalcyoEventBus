package xyz.matthewtgm.simpleeventbus;

/**
 * Base event class.
 *
 * @author MatthewTGM
 * @since 1.0
 */
public class Event {

    private boolean cancelled;
    public boolean isCancelled() {
        if (!isCancellable()) throw new IllegalArgumentException("Attempted to check for cancel with an uncancellable event.");
        return cancelled;
    }
    public void setCancelled(boolean cancelled) {
        if (!isCancellable()) throw new IllegalArgumentException("Attempted to cancel an uncancellable event.");
        this.cancelled = cancelled;
    }
    public boolean isCancellable() {
        boolean cancellable;
        Class<?> superClass = getClass().getSuperclass();
        while (superClass != null && !superClass.getName().equals(EventCancellable.class.getName()))
            superClass = superClass.getSuperclass();
        if (superClass != null) cancellable = true;
        else cancellable = getClass().isAnnotationPresent(Cancellable.class);
        return cancellable;
    }

}