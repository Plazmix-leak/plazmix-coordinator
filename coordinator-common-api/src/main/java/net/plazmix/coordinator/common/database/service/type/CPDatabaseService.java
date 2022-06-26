package net.plazmix.coordinator.common.database.service.type;

import lombok.Getter;
import net.plazmix.coordinator.common.database.LocalDatabase;
import net.plazmix.coordinator.common.database.service.LocalDatabaseService;
import net.plazmix.coordinator.common.database.service.PropertyCredentials;
import net.plazmix.coordinator.common.database.service.credential.ClasspathCredentials;
import org.itzstonlex.recon.util.InputUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Getter
public final class CPDatabaseService implements LocalDatabaseService {
    private final ClasspathCredentials credentials = new ClasspathCredentials();

    @Override
    public ByteArrayInputStream load() {

        PropertyCredentials.Result result = credentials.validate() ? PropertyCredentials.Result.SUCCESS : credentials.join();
        URL url = credentials.getClasspathUrl();

        if (!credentials.validate() || result != PropertyCredentials.Result.SUCCESS) {
            return null;
        }

        try (InputStream inputStream = url.openStream()) {
            return new ByteArrayInputStream(InputUtils.toByteArray(inputStream));
        }
        catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void create(String targetPath, LocalDatabase database) {
        throw new UnsupportedOperationException("classpath");
    }

}
