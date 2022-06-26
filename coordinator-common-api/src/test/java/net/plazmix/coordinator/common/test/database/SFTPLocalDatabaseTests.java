package net.plazmix.coordinator.common.test.database;

import net.plazmix.coordinator.common.PlazmixCommonApi;
import net.plazmix.coordinator.common.database.LocalDatabaseProvider;
import net.plazmix.coordinator.common.database.service.DatabaseServices;
import net.plazmix.coordinator.common.database.service.credential.IPCredentials;
import net.plazmix.coordinator.common.database.type.YamlLocalDatabase;

public class SFTPLocalDatabaseTests {

    public static final PlazmixCommonApi PLAZMIX_COMMON_API = new PlazmixCommonApi();
    public static final LocalDatabaseProvider LOCAL_DATABASE_PROVIDER = PLAZMIX_COMMON_API.getLocalDatabaseProvider();

    public static void main(String[] args) {
        IPCredentials credentials = new IPCredentials();

        credentials.setServer("127.0.0.1");
        credentials.setPort(22);
        credentials.setUsername("root");
        credentials.setPassword("123qweasdzxc");
        credentials.setFilepath("/home/database/messages.yml");

        YamlLocalDatabase database = LOCAL_DATABASE_PROVIDER.createFactory(DatabaseServices.SFTP, credentials)
                .newLocalDatabase(YamlLocalDatabase.class);

        if (database != null) {

            String successMessage = database.getString("messages.successMessage");
            String errorMessage = database.getString("messages.errorMessage");

            System.out.println(successMessage);
            System.out.println(errorMessage);
        }
    }

}
