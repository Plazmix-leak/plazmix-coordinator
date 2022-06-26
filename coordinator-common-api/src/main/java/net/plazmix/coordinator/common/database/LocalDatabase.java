package net.plazmix.coordinator.common.database;

import net.plazmix.coordinator.common.database.service.LocalDatabaseService;

import java.io.ByteArrayInputStream;

public interface LocalDatabase {

    void store();

    void reload();

    void init(LocalDatabaseService service, ByteArrayInputStream data);
}
