package net.plazmix.coordinator.event.impl.plugin;

import net.plazmix.coordinator.plugin.Plugin;

public class PluginDisableEvent extends PluginEvent {

    public PluginDisableEvent(Plugin plugin) {
        super(plugin);
    }
}
