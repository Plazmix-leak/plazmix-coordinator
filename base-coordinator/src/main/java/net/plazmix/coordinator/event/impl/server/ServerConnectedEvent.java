package net.plazmix.coordinator.event.impl.server;

import net.plazmix.coordinator.server.ConnectedServer;

public class ServerConnectedEvent extends ServerEvent {

    public ServerConnectedEvent(ConnectedServer server) {
        super(server);
    }
}
