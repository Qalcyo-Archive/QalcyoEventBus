import xyz.qalcyo.eventbus.SubscribeEvent;

public class TestListener implements TestInterface {

    public static final TestListener INSTANCE = new TestListener();

    @SubscribeEvent
    private void onCustomTest(TestEvent event) {
        System.out.println("Standard test completed!");
    }

}