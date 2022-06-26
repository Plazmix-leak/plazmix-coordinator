package net.plazmix.coordinator.player;

import org.itzstonlex.recon.minecraft.player.MinecraftPlayer;

import java.net.InetSocketAddress;
import java.util.UUID;

public class ConnectedPlayer extends MinecraftPlayer {

    public ConnectedPlayer(UUID uniqueId, String name, InetSocketAddress address) {
        super(uniqueId, name, address);
    }

}
