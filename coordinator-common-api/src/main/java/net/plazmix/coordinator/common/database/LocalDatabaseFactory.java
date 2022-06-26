package net.plazmix.coordinator.common.database;

import net.plazmix.coordinator.common.database.service.LocalDatabaseService;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public record LocalDatabaseFactory(LocalDatabaseService service, ByteArrayInputStream data) {

    private static final Map<Class<?>, Supplier<? extends LocalDatabase>> instancesMap
            = new HashMap<>();

    static <Database extends LocalDatabase> void addDatabaseInstance(Class<Database> clazz, Supplier<Database> supplier) {
        instancesMap.put(clazz, supplier);
    }

    @SuppressWarnings("unchecked")
    public <Database extends LocalDatabase> Database newLocalDatabase(Class<Database> clazz) {

        Supplier<Database> databaseSupplier = (Supplier<Database>) instancesMap.get(clazz);
        if (databaseSupplier == null) {
            return null;
        }

        Database instance = databaseSupplier.get();
        instance.init(service, data);

        return instance;
    }

}
