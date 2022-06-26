package net.plazmix.coordinator.common.database.service.type;

import lombok.Getter;
import net.plazmix.coordinator.common.database.LocalDatabase;
import net.plazmix.coordinator.common.database.service.LocalDatabaseService;
import net.plazmix.coordinator.common.database.service.PropertyCredentials;
import net.plazmix.coordinator.common.database.service.credential.WebURLCredentials;
import org.itzstonlex.recon.util.InputUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Getter
public final class HTTPDatabaseService implements LocalDatabaseService {
    private final WebURLCredentials credentials = new WebURLCredentials();

    @Override
    public ByteArrayInputStream load() {

        PropertyCredentials.Result result = credentials.validate() ? PropertyCredentials.Result.SUCCESS : credentials.join();
        URL url = credentials.getWebUrl();

        if (!credentials.validate() || result != PropertyCredentials.Result.SUCCESS) {
            return null;
        }

        try (InputStream inputStream = url.openConnection().getInputStream()) {
            return new ByteArrayInputStream(InputUtils.toByteArray(inputStream));
        }
        catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void create(String targetPath, LocalDatabase database) {
        throw new UnsupportedOperationException("http");
    }

}
