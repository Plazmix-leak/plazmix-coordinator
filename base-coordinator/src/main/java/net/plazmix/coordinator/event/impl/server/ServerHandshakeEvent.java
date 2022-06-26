package net.plazmix.coordinator.event.impl.server;

import net.plazmix.coordinator.server.ConnectedServer;

public class ServerHandshakeEvent extends ServerEvent {

    public ServerHandshakeEvent(ConnectedServer server) {
        super(server);
    }
}
