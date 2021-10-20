import xyz.qalcyo.eventbus.SubscribeEvent;

public class TestListener {

    public static final TestListener INSTANCE = new TestListener();

    @SubscribeEvent
    private void onTest(TestEvent event) {
        System.out.println("Standard test completed!");
    }

}