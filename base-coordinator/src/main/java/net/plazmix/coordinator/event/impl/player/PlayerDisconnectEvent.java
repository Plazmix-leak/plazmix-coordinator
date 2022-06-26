package net.plazmix.coordinator.event.impl.player;

import net.plazmix.coordinator.player.ConnectedPlayer;

public class PlayerDisconnectEvent extends PlayerEvent {

    public PlayerDisconnectEvent(ConnectedPlayer player) {
        super(player);
    }
}
