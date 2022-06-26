package net.plazmix.coordinator.common.test.database;

import net.plazmix.coordinator.common.PlazmixCommonApi;
import net.plazmix.coordinator.common.database.LocalDatabaseProvider;
import net.plazmix.coordinator.common.database.service.DatabaseServices;
import net.plazmix.coordinator.common.database.service.credential.PathCredentials;
import net.plazmix.coordinator.common.database.type.TextLocalDatabase;

public class FSLocalDatabaseTests {

    public static final PlazmixCommonApi PLAZMIX_COMMON_API = new PlazmixCommonApi();
    public static final LocalDatabaseProvider LOCAL_DATABASE_PROVIDER = PLAZMIX_COMMON_API.getLocalDatabaseProvider();

    public static void main(String[] args) {
        PathCredentials credentials = new PathCredentials();
        credentials.setPath("/resources/database.txt");

        TextLocalDatabase database = LOCAL_DATABASE_PROVIDER.createFactory(DatabaseServices.FILESYSTEM, credentials)
                .newLocalDatabase(TextLocalDatabase.class);

        if (database != null) {
            database.append("Данный вид хранения данных содержит в себе\n");
            database.append("функционал обычного текстового документа");

            String text = database.getText();
            database.rewrite("");

            System.out.println(text);
        }
    }

}
