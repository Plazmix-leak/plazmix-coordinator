package net.plazmix.coordinator.event.impl.plugin;

import net.plazmix.coordinator.event.Event;
import net.plazmix.coordinator.plugin.Plugin;

public class PluginLoadEvent extends PluginEvent {

    public PluginLoadEvent(Plugin plugin) {
        super(plugin);
    }
}
