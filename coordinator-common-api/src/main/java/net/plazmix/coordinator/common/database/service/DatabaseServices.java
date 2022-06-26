package net.plazmix.coordinator.common.database.service;

import lombok.RequiredArgsConstructor;
import net.plazmix.coordinator.common.database.service.type.CPDatabaseService;
import net.plazmix.coordinator.common.database.service.type.FSDatabaseService;
import net.plazmix.coordinator.common.database.service.type.HTTPDatabaseService;
import net.plazmix.coordinator.common.database.service.type.SFTPDatabaseService;

import java.util.function.Supplier;

@RequiredArgsConstructor
public enum DatabaseServices {

    CLASSPATH(CPDatabaseService::new),

    FILESYSTEM(FSDatabaseService::new),

    SFTP(SFTPDatabaseService::new),

    HTTP(HTTPDatabaseService::new);

    private final Supplier<LocalDatabaseService> instanceGetter;

    public LocalDatabaseService newService() {
        return instanceGetter.get();
    }
}
