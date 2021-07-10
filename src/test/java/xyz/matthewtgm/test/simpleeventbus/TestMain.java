package xyz.matthewtgm.test.simpleeventbus;

import xyz.matthewtgm.simpleeventbus.EventSubscriber;
import xyz.matthewtgm.simpleeventbus.GlobalEventBus;
import xyz.matthewtgm.simpleeventbus.SimpleEventBus;
import xyz.matthewtgm.simpleeventbus.events.ShutdownEvent;

public class TestMain {

    private TestMain() {
        start();
    }

    public static void main(String[] args) {
        new TestMain();
    }

    public void start() {
        SimpleEventBus bus = GlobalEventBus.bus();
        bus.register(this);
    }

    /*
    * Allows testing of the multiple event call attempting.
    */
    @EventSubscriber
    private void onShutdown(ShutdownEvent event) {
        System.out.println("Shutting down... (" + event.hook + ")");
        event.setCancelled(true);
        throw new RuntimeException("LLL");
    }

}