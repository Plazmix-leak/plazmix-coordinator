package net.plazmix.coordinator.event.impl.plugin;

import net.plazmix.coordinator.plugin.Plugin;

public class PluginEnableEvent extends PluginEvent {

    public PluginEnableEvent(Plugin plugin) {
        super(plugin);
    }
}
