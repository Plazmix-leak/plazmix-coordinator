package net.plazmix.coordinator.common.auth.twofactor;

import lombok.RequiredArgsConstructor;
import net.plazmix.coordinator.common.lang.LangDatabase;

@RequiredArgsConstructor
public enum TwofactorService {

    TELEGRAM("AUTH_TWOFACTOR_SERVICE_TELEGRAM") {

        @Override
        public void loadSession(TwofactorSession session) {
            // nothing.
        }
    },
    DISCORD("AUTH_TWOFACTOR_SERVICE_DISCORD") {

        @Override
        public void loadSession(TwofactorSession session) {
            // nothing.
        }
    },
    VKONTAKTE("AUTH_TWOFACTOR_SERVICE_VKONTAKTE") {

        @Override
        public void loadSession(TwofactorSession session) {
            // nothing.
        }
    },
    EMAIL("AUTH_TWOFACTOR_SERVICE_EMAIL") {

        @Override
        public void loadSession(TwofactorSession session) {
            // nothing.
        }
    },
    ;

    private final String name;

    abstract void loadSession(TwofactorSession session);

    public String getName(LangDatabase langDatabase) {
        return langDatabase.getColouredString(name);
    }
}
