package xyz.matthewtgm.simpleeventbus;

/**
 * @author MatthewTGM
 * @since 1.0
 */
public enum EventPriority {
    FIRST(0),
    SECOND(1),
    THIRD(2),
    FOURTH(3),
    FIFTH(4);
    
    private final int priority;
    EventPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}