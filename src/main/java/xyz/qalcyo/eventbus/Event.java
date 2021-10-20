package xyz.qalcyo.eventbus;

public abstract class Event {

    private boolean cancelled;

    public boolean isCancellable() {
        return this instanceof CancellableEvent || getClass().isAnnotationPresent(Cancellable.class);
    }

    public boolean isCancelled() {
        if (!isCancellable()) {
            return false;
        }

        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        if (!isCancellable()) {
            throw new IllegalArgumentException("Cannot cancel an uncancellable event!");
        }

        this.cancelled = cancelled;
    }

    public void cancel() {
        if (!isCancellable()) {
            throw new IllegalArgumentException("Cannot cancel an uncancellable event!");
        }

        setCancelled(true);
    }

    public void uncancel() {
        if (!isCancellable()) {
            throw new IllegalArgumentException("Cannot uncancel an uncancellable event!");
        }

        setCancelled(false);
    }

}