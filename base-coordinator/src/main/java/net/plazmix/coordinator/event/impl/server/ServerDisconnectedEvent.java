package net.plazmix.coordinator.event.impl.server;

import net.plazmix.coordinator.server.ConnectedServer;

public class ServerDisconnectedEvent extends ServerEvent {

    public ServerDisconnectedEvent(ConnectedServer server) {
        super(server);
    }
}
