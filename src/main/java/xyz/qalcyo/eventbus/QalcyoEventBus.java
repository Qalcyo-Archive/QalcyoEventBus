package xyz.qalcyo.eventbus;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

public class QalcyoEventBus {

    private static final QalcyoEventBus GLOBAL_BUS = new QalcyoEventBus();
    private final Map<Class<? extends Event>, ArrayList<EventData>> registry = new HashMap<>();

    private boolean isBadMethod(Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(SubscribeEvent.class);
    }

    private void sortList(Class<? extends Event> clazz) {
        ArrayList<EventData> flexible = new ArrayList<>();
        for (EventPriority priority : EventPriority.values()) {
            for (EventData data : registry.get(clazz)) {
                if (data.priority == priority) {
                    flexible.add(data);
                }
            }
        }

        registry.put(clazz, flexible);
    }

    public void cleanRegistry() {
        registry.entrySet().removeIf(entry -> entry.getValue() == null || entry.getValue().isEmpty());
    }

    private void register(Class<? extends Event> event, Method method, Object instance) {
        EventData data = new EventData(instance, method, method.getAnnotation(SubscribeEvent.class).priority());
        data.target.setAccessible(true);
        if (registry.containsKey(event)) {
            if (!registry.get(event).contains(data)) {
                registry.get(event).add(data);
                sortList(event);
            }
        } else {
            registry.put(event, new ArrayList<EventData>() {
                {
                    add(data);
                }
            });
        }
    }

    public void register(Object instance) {
        Objects.requireNonNull(instance);
        for (Method method : instance.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (!isBadMethod(method)) {
                register((Class<? extends Event>) method.getParameterTypes()[0], method, instance);
            }
        }
    }

    public <T extends Event> void register(Class<T> clazz, Consumer<T> processor) {
        try {
            PseudoSubscriber<T> subscriber = new PseudoSubscriber<>(processor);
            register(clazz, subscriber.getClass().getDeclaredMethod("onEvent", Event.class), subscriber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unregister(Object instance) {
        for (ArrayList<EventData> value : registry.values()) {
            value.removeIf(data -> data.source.equals(instance));
        }

        cleanRegistry();
    }

    public void post(Event event) {
        try {
            for (EventData data : registry.get(event.getClass())) {
                data.target.invoke(data.source, event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<Class<? extends Event>, ArrayList<EventData>> getRegistry() {
        return registry;
    }

    public static QalcyoEventBus getGlobalBus() {
        return GLOBAL_BUS;
    }

}