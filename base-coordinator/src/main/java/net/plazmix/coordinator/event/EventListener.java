package net.plazmix.coordinator.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {

    int LOWEST_PRIORITY  = -32;
    int LOW_PRIORITY     = -16;
    int MONITOR_PRIORITY = 0;
    int NORMAL_PRIORITY  = 1;
    int HIGH_PRIORITY    = 16;
    int HIGHEST_PRIORITY = 32;

    boolean ignoreCancelled() default false;

    int priority() default NORMAL_PRIORITY;
}
