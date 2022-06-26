package net.plazmix.coordinator.event.impl.player;

import net.plazmix.coordinator.player.ConnectedPlayer;

public class PlayerJoinEvent extends PlayerEvent {

    public PlayerJoinEvent(ConnectedPlayer player) {
        super(player);
    }
}
