package net.plazmix.coordinator.event.impl.player;

import net.plazmix.coordinator.player.ConnectedPlayer;

public class PlayerHandshakeEvent extends PlayerEvent {

    public PlayerHandshakeEvent(ConnectedPlayer player) {
        super(player);
    }
}
