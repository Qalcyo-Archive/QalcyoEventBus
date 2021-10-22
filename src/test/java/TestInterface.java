import xyz.qalcyo.eventbus.SubscribeEvent;

public interface TestInterface extends TestInterfaceDeep {
    @SubscribeEvent
    default void onTest(TestEvent event) {
        System.out.println("WEE WOO WEE WOO");
    }
}