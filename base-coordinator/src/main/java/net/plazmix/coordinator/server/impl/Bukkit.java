package net.plazmix.coordinator.server.impl;

import net.plazmix.coordinator.PlazmixCoordinator;
import net.plazmix.coordinator.server.ConnectedServer;
import org.itzstonlex.recon.RemoteChannel;

import java.net.InetSocketAddress;

public class Bukkit extends ConnectedServer {

    public Bukkit(PlazmixCoordinator coordinator, RemoteChannel channel, String name, InetSocketAddress address) {
        super(coordinator, channel, false, name, address);
    }

}
