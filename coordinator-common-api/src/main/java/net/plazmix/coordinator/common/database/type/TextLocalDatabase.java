package net.plazmix.coordinator.common.database.type;

import net.plazmix.coordinator.common.database.LocalDatabase;
import net.plazmix.coordinator.common.database.service.LocalDatabaseService;

import java.io.ByteArrayInputStream;
import java.util.function.Supplier;

public class TextLocalDatabase implements LocalDatabase {

    private LocalDatabaseService service;
    private String text;

    public String getText() {
        return text;
    }

    public void rewrite(String text) {
        this.text = text;
    }

    public void append(String appendText) {

    }

    @Override
    public void store() {
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
