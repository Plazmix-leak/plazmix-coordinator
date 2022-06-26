package net.plazmix.coordinator.common;

import lombok.Getter;
import net.plazmix.coordinator.common.auth.AuthController;
import net.plazmix.coordinator.common.database.LocalDatabaseProvider;
import net.plazmix.coordinator.common.lang.LangLoader;

@Getter
public final class PlazmixCommonApi {

    private final LangLoader langLoader = new LangLoader();

    private final AuthController authController = new AuthController();

    private final LocalDatabaseProvider localDatabaseProvider = new LocalDatabaseProvider();
}
