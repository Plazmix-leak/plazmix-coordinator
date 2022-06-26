package net.plazmix.coordinator.common.database.service.type;

import lombok.Getter;
import net.plazmix.coordinator.common.database.LocalDatabase;
import net.plazmix.coordinator.common.database.service.LocalDatabaseService;
import net.plazmix.coordinator.common.database.service.PropertyCredentials;
import net.plazmix.coordinator.common.database.service.credential.IPCredentials;
import org.itzstonlex.recon.http.ftp.HttpFTPClient;
import org.itzstonlex.recon.util.InputUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Getter
public final class SFTPDatabaseService implements LocalDatabaseService {

    private final HttpFTPClient httpFTPClient = new HttpFTPClient();
    private final IPCredentials credentials = new IPCredentials();

    @Override
    public ByteArrayInputStream load() {

        PropertyCredentials.Result result = credentials.validate() ? PropertyCredentials.Result.SUCCESS : credentials.join();
        boolean isConnected = httpFTPClient.connect(credentials.getServer(), credentials.getPort(), credentials.getUsername(), credentials.getPassword());

        if (!isConnected || !credentials.validate() || result != PropertyCredentials.Result.SUCCESS) {
            return null;
        }

        try (InputStream inputStream = httpFTPClient.loadFile(credentials.getFilepath())) {
            return new ByteArrayInputStream(InputUtils.toByteArray(inputStream));
        }
        catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void create(String targetPath, LocalDatabase database) {
        if (!httpFTPClient.isConnected()) {
            return;
        }

        if (targetPath.contains(".")) {
            httpFTPClient.createFile(targetPath);
        }
        else {
            httpFTPClient.createDirectories(targetPath);
        }
    }

}
