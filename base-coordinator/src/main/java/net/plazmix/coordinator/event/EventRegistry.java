package net.plazmix.coordinator.event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import lombok.NonNull;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public final class EventRegistry {

    private final ExecutorService threadExecutor = Executors.newCachedThreadPool();
    private final SetMultimap<Class<? extends Event>, Consumer<Event>> eventHandlersMap = new HashMultimap<>();

    public <E extends Event> void registerEventHandler(Class<E> clazz, Consumer<E> eventHandler) {
        eventHandlersMap.put(clazz, (Consumer<Event>) eventHandler);
    }

    public void registerEventHandler(Object listener) {
        threadExecutor.execute(() -> {
            try {

                for (Method method : listener.getClass().getDeclaredMethods()) {
                    if (method.getParameterCount() != 1 || !method.getParameterTypes()[0].isAssignableFrom(Event.class)) {
                        continue;
                    }

                    EventListener annotation = method.getAnnotation(EventListener.class);
                    if (annotation == null) {
                        continue;
                    }

                    // TODO - 04.02.2022 - add priorities detection.
                    registerEventHandler((Class<? extends Event>) method.getParameterTypes()[0], event -> {
                        try {
                            method.invoke(listener, event);
                        }
                        catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    });
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public void fireEvent(@NonNull Event event) {
        eventHandlersMap.get(event.getClass()).forEach(consumer -> consumer.accept(event));
    }

}
