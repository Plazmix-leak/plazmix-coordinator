package net.plazmix.coordinator;

import lombok.Getter;
import net.plazmix.coordinator.event.EventRegistry;
import net.plazmix.coordinator.handler.PendingChannelHandler;
import net.plazmix.coordinator.player.ConnectedPlayer;
import net.plazmix.coordinator.server.ConnectedServer;
import net.plazmix.coordinator.server.impl.Bukkit;
import net.plazmix.coordinator.server.impl.Proxy;
import org.itzstonlex.recon.RemoteChannel;
import org.itzstonlex.recon.minecraft.PendingConnection;
import org.itzstonlex.recon.minecraft.api.ReconMinecraftApi;
import org.itzstonlex.recon.minecraft.api.ReconMinecraftRegistry;
import org.itzstonlex.recon.minecraft.packet.MinecraftPacket;
import org.itzstonlex.recon.minecraft.server.MinecraftServersGroup;

import java.io.File;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.UUID;

@Getter
public final class PlazmixCoordinator implements PendingConnection {

    public static final ReconMinecraftApi MINECRAFT_API = new ReconMinecraftApi();

    @Getter
    private static PlazmixCoordinator instance;

    private RemoteChannel channel;

    private final Path folder                   = new File("").toPath();

    private final EventRegistry eventRegistry   = new EventRegistry();
    private final PlazmixService service        = new PlazmixService(this);

    PlazmixCoordinator() {
        instance = this;
    }

    /**
     * Process of the application launch.
     */
    void launch() {
        // ...your launch code

        bindLocal();
    }

    /**
     * Bind a Local Server on
     * the port of 1000.
     */
    private void bindLocal() {
        ReconMinecraftApi.MinecraftApplication application = MINECRAFT_API.newMinecraftApplication();

        // Create a service-factory.
        ReconMinecraftApi.ServiceFactory serviceFactory = ReconMinecraftApi.ServiceFactory.create(service,
                (bossHandler, channelConfig) -> {

                    // Registry managements.
                    ReconMinecraftRegistry registry = MINECRAFT_API.getRegistry();

                    registerServersGroups(registry);
                    registerPackets(registry);

                    // Pipeline managements.
                    channelConfig.pipeline().addLast("channel-handler", new PendingChannelHandler(this));
                });

        // Bind a minecraft application server.
        int port = Launcher.getIntProperty(Launcher.PROPERTY_COORDINATOR_PORT_KEY);
        String host = Launcher.getStringProperty(Launcher.PROPERTY_COORDINATOR_HOST_KEY);

        this.channel = application.bind(host, port, serviceFactory);
    }

    /**
     * Registering all packets.
     *
     * ATTENTION! The system has already pre-occupied
     * identifiers 0 and 1 handshake packages,
     * so if you want to specify identifiers
     * manually, pay attention to this information
     *
     * @param registry - Recon Minecraft registry-service.
     */
    private void registerPackets(ReconMinecraftRegistry registry) {
        // ...
    }

    /**
     * Registering all packets.
     *
     * This is where the basic server
     * groups are registered.
     *
     * Thanks to them, it will be possible
     * to find out which department
     * a certain server belongs to.
     *
     * It is important to know that it
     * works exclusively by prefixes from
     * the names of the connected servers.
     *
     * @param registry - Recon Minecraft registry-service.
     */
    private void registerServersGroups(ReconMinecraftRegistry registry) {
        registry.registerServersGroup(MinecraftServersGroup.create(1, "Proxy", "bungee"));
        registry.registerServersGroup(MinecraftServersGroup.create(2, "Bukkit", null));
    }


    /**
     * Getting a player by UUID
     *
     * @param uuid - Player UUID
     */
    public ConnectedPlayer getPlayer(UUID uuid) {
        return service.getPlayerManager().getPlayer(uuid);
    }

    /**
     * Getting a player by name.
     *
     * @param name - Player name.
     */
    public ConnectedPlayer getPlayer(String name) {
        return service.getPlayerManager().getPlayer(name);
    }

    /**
     * Getting a proxy server by name.
     *
     * @param name - Proxy server name.
     */
    public Proxy getProxy(String name) {
        ConnectedServer server = service.getServerManager().getServer(name);

        if (server.isProxy()) {
            return (Proxy) server;
        }

        return null;
    }

    /**
     * Getting a bukkit server by name.
     *
     * @param name - Bukkit server name.
     */
    public Bukkit getBukkit(String name) {
        ConnectedServer server = service.getServerManager().getServer(name);

        if (!server.isProxy()) {
            return (Bukkit) server;
        }

        return null;
    }

    @Override
    public String getName() {
        return "PlazmixCoordinator";
    }

    @Override
    public InetSocketAddress getAddress() {
        return channel.address();
    }

    @Override
    public RemoteChannel getChannel() {
        return channel;
    }

    @Override
    public void onConnected(RemoteChannel channel) {
        channel.logger().info("[Plazmix] Channel was success listening on " + channel.address());
    }

    @Override
    public void onDisconnected(RemoteChannel channel) {
        channel.logger().info("[Plazmix] Channel was closed!");

        System.exit(0);
    }

    @Override
    public void sendPacket(MinecraftPacket packet) {
        MINECRAFT_API.sendPacket(channel, packet);
    }
}
