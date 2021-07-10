package xyz.matthewtgm.simpleeventbus;

import xyz.matthewtgm.simpleeventbus.events.ShutdownEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MatthewTGM
 * @since 1.0
 */
@SuppressWarnings({"unused", "unchecked"})
public class SimpleEventBus {

    static boolean shutdownEventRegistered;
    private static final Map<Class<? extends Event>, ArrayList<EventData>> REGISTRY_MAP = new HashMap<>();
    private int eventCallAttempts;

    public SimpleEventBus() {
        if (!shutdownEventRegistered) {
            shutdownEventRegistered = true;
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    call(new ShutdownEvent(this));
                }
            }, "SimpleEventBus Shutdown"));
        }
    }

    private void sortListValue(Class<? extends Event> clazz) {
        ArrayList<EventData> flexibleArray = new ArrayList<>();
        for (EventPriority priority : EventPriority.values())
            for (EventData methodData : REGISTRY_MAP.get(clazz))
                if (methodData.priority == priority)
                    flexibleArray.add(methodData);
        REGISTRY_MAP.put(clazz, flexibleArray);
    }

    private boolean isMethodBad(Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventSubscriber.class);
    }

    private ArrayList<EventData> get(final Class<? extends Event> clazz) {
        return REGISTRY_MAP.get(clazz);
    }

    private void cleanMap() {
        REGISTRY_MAP.entrySet().removeIf(classArrayListEntry -> classArrayListEntry.getValue().isEmpty());
    }

    /**
     * Removes the object from the registry map, making all subscribed methods unsubscribed.
     *
     * @param o The object to unregister.
     *
     * @author MatthewTGM
     * @since 1.0
     */
    public void unregister(Object o) {
        for (ArrayList<EventData> flexibleArray : REGISTRY_MAP.values())
            for (int i = flexibleArray.size() - 1; i >= 0; i--)
                if (flexibleArray.get(i).source.equals(o))
                    flexibleArray.remove(i);
        cleanMap();
    }

    private void register(Method method, Object o) {
        Class<?> clazz = method.getParameterTypes()[0];
        EventData methodData = new EventData(o, method, method.getAnnotation(EventSubscriber.class).priority());
        methodData.target.setAccessible(true);
        if (REGISTRY_MAP.containsKey(clazz)) {
            if (!REGISTRY_MAP.get(clazz).contains(methodData)) {
                REGISTRY_MAP.get(clazz).add(methodData);
                sortListValue((Class<? extends Event>) clazz);
            }
        } else {
            REGISTRY_MAP.put((Class<? extends Event>) clazz, new ArrayList<EventData>() {
                {
                    add(methodData);
                }
            });
        }
    }

    /**
     * Registers an object as an event subscriber.
     *
     * @author MatthewTGM
     * @since 1.0
     */
    public void register(Object o) {
        for (Method method : o.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (!isMethodBad(method))
                register(method, o);
        }
    }

    /**
     * Registers multiple objects as event subscribers.
     *
     * @param o The objects to register.
     *
     * @author MatthewTGM
     * @since 1.0
     */
    public void register(Object... o) {
        for (Object clz : o)
            register(clz);
    }

    /**
     * Calls the event passed into the parameters.
     *
     * @param event The event to call.
     *
     * @author MatthewTGM
     * @since 1.0
     */
    public void call(Event event) {
        List<EventData> dataList = get(event.getClass());
        if (eventCallAttempts < 5 && dataList != null) {
            try {
                for (EventData data : dataList)
                    data.target.invoke(data.source, event);
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getMessage().equalsIgnoreCase("Attempted to cancel an uncancellable event."))
                    return;
                eventCallAttempts++;
                System.out.println("Failed to call " + event.getClass().getSimpleName() + ". Attempting to call it for the " + getNumberWithPrefix(eventCallAttempts) + " time.");
                call(event);
            }
        }
    }

    public void callBasic(Event event) {
        List<EventData> dataList = get(event.getClass());
        if (dataList != null) {
            try {
                for (EventData data : dataList)
                    data.target.invoke(data.source, event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getNumberWithPrefix(int num) {
        if (num <= 1)
            return num + "st";
        else if (num == 2)
            return num + "nd";
        else if (num == 3)
            return num + "rd";
        else if (num >= 4)
            return num + "th";
        else
            return String.valueOf(num);
    }

    private static class EventData {
        public final Object source;
        public final Method target;
        public final EventPriority priority;

        public EventData(Object source, Method target, EventPriority priority) {
            this.source = source;
            this.target = target;
            this.priority = priority;
        }
    }

}