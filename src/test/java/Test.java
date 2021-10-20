import xyz.qalcyo.eventbus.QalcyoEventBus;

public class Test {

    private static final Test INSTANCE = new Test();

    public void start() {
        QalcyoEventBus.getGlobalBus().register(TestEvent.class, this::onConsumerTest);
        QalcyoEventBus.getGlobalBus().register(TestListener.INSTANCE);

        QalcyoEventBus.getGlobalBus().post(new TestEvent());
    }

    private void onConsumerTest(TestEvent event) {
        System.out.println("Consumer test completed!");
    }

    public static void main(String[] args) {
        INSTANCE.start();
    }

}