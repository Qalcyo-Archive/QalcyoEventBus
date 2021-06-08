package xyz.matthewtgm.simpleeventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to determine whether a method is to be called when an event is called.
 *
 * @author MatthewTGM
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventSubscriber {
    EventPriority priority() default EventPriority.THIRD;
}