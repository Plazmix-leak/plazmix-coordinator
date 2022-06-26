package net.plazmix.coordinator.event.impl.player;

import net.plazmix.coordinator.player.ConnectedPlayer;

public class PlayerRedirectEvent extends PlayerEvent {

    public PlayerRedirectEvent(ConnectedPlayer player) {
        super(player);
    }
}
