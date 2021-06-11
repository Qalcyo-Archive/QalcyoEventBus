package xyz.matthewtgm.test.simpleeventbus;

import xyz.matthewtgm.simpleeventbus.EventSubscriber;
import xyz.matthewtgm.simpleeventbus.SimpleEventBus;
import xyz.matthewtgm.simpleeventbus.events.EventListenerRegisteredEvent;
import xyz.matthewtgm.simpleeventbus.events.ShutdownEvent;

public class TestMain {

    private final SimpleEventBus eventBus = new SimpleEventBus();

    private TestMain() {
        start();
    }

    public static void main(String[] args) {
        new TestMain();
    }

    public void start() {
        eventBus.register(this);
    }

    /*
    * Allows testing of the event listener register event, which is called when an event listener is registered.
    */
    @EventSubscriber
    private void onEventListenerRegistered(EventListenerRegisteredEvent event) {
        System.out.println("An event listener was registered! (Class: " + event.clazz.getSimpleName() + " | Method: " + event.method.getName() + ")");
    }

    /*
    * Allows testing of the multiple event call attempting.
    */
    @EventSubscriber
    private void onShutdown(ShutdownEvent event) {
        System.out.println("Shutting down...");
        throw new RuntimeException("L");
    }

}