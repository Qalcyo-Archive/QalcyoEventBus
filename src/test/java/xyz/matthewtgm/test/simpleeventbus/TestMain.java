package xyz.matthewtgm.test.simpleeventbus;

import xyz.matthewtgm.simpleeventbus.EventSubscriber;
import xyz.matthewtgm.simpleeventbus.GlobalEventBus;
import xyz.matthewtgm.simpleeventbus.SimpleEventBus;
import xyz.matthewtgm.simpleeventbus.events.CallFailedEvent;
import xyz.matthewtgm.simpleeventbus.events.ListenerRegisteredEvent;
import xyz.matthewtgm.simpleeventbus.events.ShutdownEvent;

public class TestMain {

    private TestMain() {
        start();
    }

    public static void main(String[] args) {
        new TestMain();
    }

    public void start() {
        SimpleEventBus bus = GlobalEventBus.get();
        bus.register(this);
    }

    /*
    * Allows testing of the event listener register event, which is called when an event listener is registered.
    */
    @EventSubscriber
    private void onEventListenerRegistered(ListenerRegisteredEvent event) {
        System.out.println("An event listener was registered! (Class: " + event.clazz.getSimpleName() + " | Method: " + event.method.getName() + ")");
    }

    @EventSubscriber
    private void onEventCallFailed(CallFailedEvent event) {
        System.out.println(event.event + " :(");
    }

    /*
    * Allows testing of the multiple event call attempting.
    */
    @EventSubscriber
    private void onShutdown(ShutdownEvent event) {
        System.out.println("Shutting down... (" + event.hook + ")");
        throw new RuntimeException("LLL");
    }

}