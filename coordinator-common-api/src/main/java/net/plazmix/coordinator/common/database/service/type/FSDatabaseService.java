package net.plazmix.coordinator.common.database.service.type;

import lombok.Getter;
import net.plazmix.coordinator.common.database.LocalDatabase;
import net.plazmix.coordinator.common.database.service.LocalDatabaseService;
import net.plazmix.coordinator.common.database.service.PropertyCredentials;
import net.plazmix.coordinator.common.database.service.credential.PathCredentials;
import org.itzstonlex.recon.util.InputUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public final class FSDatabaseService implements LocalDatabaseService {
    private final PathCredentials credentials = new PathCredentials();

    @Override
    public ByteArrayInputStream load() {

        PropertyCredentials.Result result = credentials.validate() ? PropertyCredentials.Result.SUCCESS : credentials.join();
        Path path = credentials.getPath();

        if (!credentials.validate() || result != PropertyCredentials.Result.SUCCESS) {
            return null;
        }

        try (FileInputStream inputStream = new FileInputStream(path.toFile())) {
            return new ByteArrayInputStream(InputUtils.toByteArray(inputStream));
        }
        catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void create(String targetPath, LocalDatabase database) {
        try {
            Path path = Paths.get(targetPath);

            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
