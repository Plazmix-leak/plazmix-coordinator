package net.plazmix.coordinator.common.database.type;

import net.plazmix.coordinator.common.database.LocalDatabase;
import net.plazmix.coordinator.common.database.service.LocalDatabaseService;

import java.io.ByteArrayInputStream;
import java.util.Properties;
import java.util.function.Supplier;

public class PropertyLocalDatabase implements LocalDatabase {

    private LocalDatabaseService service;
    private Properties properties;

    public void set(String key, String value) {
        properties.setProperty(key, value);
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String get(String key, String def) {
        return properties.getProperty(key, def);
    }

    public String get(String key, Supplier<String> def) {
        String instance = this.get(key);
        if (instance == null) {
            return def.get();
        }

        return instance;
    }

    @Override
    public void store() {
        // nothing
    }

    @Override
    public void reload() {
        this.init(service, service.load());
    }

    @Override
    public void init(LocalDatabaseService service, ByteArrayInputStream data) {
        this.service = service;
    }

}
