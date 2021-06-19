package xyz.matthewtgm.simpleeventbus;

/**
 * @author MatthewTGM
 * @since 1.4
 */
public class GlobalEventBus {
    private static SimpleEventBus bus;
    public static SimpleEventBus get() {
        if (bus == null) bus = new SimpleEventBus();
        return bus;
    }
}