package net.plazmix.coordinator.event.impl.player;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.plazmix.coordinator.event.Event;
import net.plazmix.coordinator.player.ConnectedPlayer;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class PlayerEvent extends Event {

    @Getter
    ConnectedPlayer player;
}
