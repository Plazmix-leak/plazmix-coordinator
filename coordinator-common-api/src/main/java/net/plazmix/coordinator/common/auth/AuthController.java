package net.plazmix.coordinator.common.auth;

import net.plazmix.coordinator.common.auth.twofactor.TwofactorSession;
import net.plazmix.coordinator.common.auth.twofactor.TwofactorService;

@SuppressWarnings("all") // убрать потом, как все заполнится реализацией, ибо поставил чисто для красоты вида.
public final class AuthController {

    public int getRegisteredAccounts() {
        return 0; // TODO - 03.02.2022 - getting from database by JDBI services.
    }

    public AuthDatabase getAuthDatabase() {
        return null; // TODO - 03.02.2022 - getting from database by JDBI services.
    }

    public String getHashedPassword(String password) {
        return null; // TODO - 03.02.2022 - add hashes.
    }

    public TwofactorSession getTwofactorSession(int playerId, TwofactorService service) {
        return new TwofactorSession(playerId, service); // TODO - 03.02.2022 - cache & load from database by JDBI services.
    }

    public void enableLicensedJoin(int playerID) {
        AuthDatabase authDatabase = getAuthDatabase();

        if (!authDatabase.getPlayerLicenseStatus(playerID)) {
            // TODO - 03.02.2022 - execute from database by JDBI services.
        }
    }

    public void disableLicensedJoin(int playerID) {
        AuthDatabase authDatabase = getAuthDatabase();

        if (authDatabase.getPlayerLicenseStatus(playerID)) {
            // TODO - 03.02.2022 - execute from database by JDBI services.
        }
    }

}
