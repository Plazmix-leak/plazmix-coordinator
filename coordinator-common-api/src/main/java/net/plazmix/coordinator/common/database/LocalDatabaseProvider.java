package net.plazmix.coordinator.common.database;

import lombok.Getter;
import net.plazmix.coordinator.common.database.service.DatabaseServices;
import net.plazmix.coordinator.common.database.service.LocalDatabaseService;
import net.plazmix.coordinator.common.database.service.PropertyCredentials;
import net.plazmix.coordinator.common.database.type.PropertyLocalDatabase;
import net.plazmix.coordinator.common.database.type.TextLocalDatabase;
import net.plazmix.coordinator.common.database.type.YamlLocalDatabase;

import java.io.ByteArrayInputStream;

public final class LocalDatabaseProvider {
    
    public LocalDatabaseProvider() {
        LocalDatabaseFactory.addDatabaseInstance(PropertyLocalDatabase.class, PropertyLocalDatabase::new);
        LocalDatabaseFactory.addDatabaseInstance(TextLocalDatabase.class, TextLocalDatabase::new);
        LocalDatabaseFactory.addDatabaseInstance(YamlLocalDatabase.class, YamlLocalDatabase::new);
    }

    public LocalDatabaseFactory createFactory(DatabaseServices services, PropertyCredentials credentials) {
        LocalDatabaseService service = services.newService();

        service.getCredentials().resetProperties();
        service.getCredentials().setProperties(credentials);
        
        ByteArrayInputStream data = service.load();
        
        if (data == null) {
            throw new RuntimeException("data is null");
        }
        
        return new LocalDatabaseFactory(service, data);
    }
    
    public void createDatabase(String targetPath, Class<? extends LocalDatabase> databaseType, 
                               DatabaseServices services, PropertyCredentials credentials) {
        try {
            LocalDatabaseFactory factory = createFactory(services, credentials);
            factory.service().create(targetPath, factory.newLocalDatabase(databaseType));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
