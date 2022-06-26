package net.plazmix.coordinator;

import net.plazmix.coordinator.player.ConnectedPlayer;
import net.plazmix.coordinator.server.ConnectedServer;
import net.plazmix.coordinator.server.impl.Bukkit;
import net.plazmix.coordinator.server.impl.Proxy;
import org.itzstonlex.recon.RemoteChannel;
import org.itzstonlex.recon.minecraft.packet.handshake.HandshakeInitializer;
import org.itzstonlex.recon.minecraft.packet.handshake.impl.PlayerHandshake;
import org.itzstonlex.recon.minecraft.packet.handshake.impl.ServerHandshake;
import org.itzstonlex.recon.minecraft.player.PlayerManager;
import org.itzstonlex.recon.minecraft.server.ServerManager;
import org.itzstonlex.recon.minecraft.service.MinecraftManagementService;

import java.net.InetSocketAddress;
import java.util.UUID;

@SuppressWarnings("unchecked")
public final class PlazmixService implements MinecraftManagementService {

    private final PlazmixCoordinator coordinator;

    private final PlayerManager<ConnectedPlayer> playerManager = PlayerManager.newManager(ConnectedPlayer.class, new PlayerInitializer());
    private final ServerManager<ConnectedServer> serverManager = ServerManager.newManager(ConnectedServer.class, new ServerInitializer());

    public PlazmixService(PlazmixCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public PlayerManager<ConnectedPlayer> getPlayerManager() {
        return playerManager;
    }

    @Override
    public ServerManager<ConnectedServer> getServerManager() {
        return serverManager;
    }


    private final class PlayerInitializer
            implements HandshakeInitializer<PlayerHandshake, ConnectedPlayer> {

        @Override
        public ConnectedPlayer init(RemoteChannel channel, PlayerHandshake handshake) {
            UUID uniqueId = handshake.getUniqueId();

            String playerName = handshake.getName();
            InetSocketAddress address = handshake.getAddress();

            ConnectedPlayer instance = new ConnectedPlayer(uniqueId, playerName, address);

            // Init non-final other data.
            instance.init(serverManager,
                    handshake.getDisplayName(), handshake.getProxyServer(), handshake.getCurrentServer()
            );

            return instance;
        }
    }

    private final class ServerInitializer
            implements HandshakeInitializer<ServerHandshake, ConnectedServer> {

        @Override
        public ConnectedServer init(RemoteChannel channel, ServerHandshake handshake) {
            String serverName = handshake.getName();
            InetSocketAddress address = handshake.getAddress();

            ConnectedServer connectedServer;

            if (handshake.isProxy()) {
                connectedServer = new Proxy(coordinator, channel, serverName, address);

            } else {

                connectedServer = new Bukkit(coordinator, channel, serverName, address);
            }

            return connectedServer;
        }
    }

}
