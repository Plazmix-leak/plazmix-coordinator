package net.plazmix.coordinator.event.impl.server;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.plazmix.coordinator.event.Event;
import net.plazmix.coordinator.server.ConnectedServer;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class ServerEvent extends Event {

    @Getter
    ConnectedServer server;
}
