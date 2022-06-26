package net.plazmix.coordinator.event.impl.plugin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.plazmix.coordinator.event.Event;
import net.plazmix.coordinator.plugin.Plugin;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class PluginEvent extends Event {

    @Getter
    Plugin plugin;
}
