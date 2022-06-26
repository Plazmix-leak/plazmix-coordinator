package net.plazmix.coordinator.event.impl.player;

import net.plazmix.coordinator.player.ConnectedPlayer;

public class PlayerKickEvent extends PlayerEvent {

    public PlayerKickEvent(ConnectedPlayer player) {
        super(player);
    }
}
