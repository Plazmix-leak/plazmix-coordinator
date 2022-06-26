package net.plazmix.coordinator.common.auth.twofactor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public final class TwofactorSession {

    private final int playerID;
    private final TwofactorService service;

    @Setter(AccessLevel.PRIVATE)
    private boolean active;

    public void loadSession() {
        service.loadSession(this);
    }

    public void startSession() {
        if (isActive()) {
            return;
        }

        setActive(true);
        // ...
    }

    public void endSession() {
        if (!isActive()) {
            return;
        }

        setActive(false);
        // ...
    }

}
