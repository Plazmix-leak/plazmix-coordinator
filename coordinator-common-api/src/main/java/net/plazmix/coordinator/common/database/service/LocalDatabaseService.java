package net.plazmix.coordinator.common.database.service;

import net.plazmix.coordinator.common.database.LocalDatabase;

import java.io.ByteArrayInputStream;

public interface LocalDatabaseService {

    PropertyCredentials getCredentials();

    ByteArrayInputStream load();

    void create(String targetPath, LocalDatabase database);
}
