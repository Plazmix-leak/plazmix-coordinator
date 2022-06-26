package net.plazmix.coordinator.common.auth;

import java.util.UUID;

// TODO - 03.02.2022 - add JDBI annotations.
public interface AuthDatabase {

    enum AuthAddresses {

        ON_REGISTER,
        ON_LAST_JOIN,
    }

    String getPlayerEmail(int playerID);

    String getPlayerEmail(String name);

    String getPlayerAddress(AuthAddresses address, int playerID);

    String getPlayerAddress(AuthAddresses address, String name);

    UUID getPlayerUUID(int playerID);

    UUID getPlayerUUID(String name);

    boolean getPlayerLicenseStatus(int playerID);

    boolean getPlayerLicenseStatus(String name);

    boolean getPlayerTwofactorAuthStatus(int playerID);

    boolean getPlayerTwofactorAuthStatus(String name);

    long getPlayerRegisterDatetime(int playerID);

    long getPlayerRegisterDatetime(String name);

    boolean isPasswordEquals(int playerID, String password);

    boolean isPasswordEquals(String name, String password);
}
