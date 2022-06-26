package net.plazmix.coordinator.server;

import net.plazmix.coordinator.PlazmixCoordinator;
import org.itzstonlex.recon.RemoteChannel;
import org.itzstonlex.recon.minecraft.player.MinecraftPlayer;
import org.itzstonlex.recon.minecraft.server.MinecraftServer;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ConnectedServer extends MinecraftServer {

    protected final PlazmixCoordinator coordinator;

    public ConnectedServer(PlazmixCoordinator coordinator, RemoteChannel channel,
                           boolean isProxy, String name, InetSocketAddress address) {

        super(PlazmixCoordinator.MINECRAFT_API, channel, isProxy, name, address);
        this.coordinator = coordinator;
    }

    public List<MinecraftPlayer> getOnlinePlayers() {
        return coordinator.getService().getPlayerManager().getPlayers()
                .stream()
                .filter(minecraftPlayer -> minecraftPlayer.getServer() != null && minecraftPlayer.getServer().getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    public int getOnlineCount() {
        return (int) coordinator.getService().getPlayerManager().getPlayers()
                .stream()
                .filter(minecraftPlayer -> minecraftPlayer.getServer() != null && minecraftPlayer.getServer().getName().equalsIgnoreCase(name))
                .count();
    }

}
