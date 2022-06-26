package net.plazmix.coordinator.common.database.service.credential;

import lombok.Getter;
import net.plazmix.coordinator.common.database.service.PropertyCredentials;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Getter
public class PathCredentials extends PropertyCredentials {
    private Path path;

    @Override
    public boolean validate() {
        return path != null && Files.exists(path);
    }

    @Override
    protected Result init(Map<String, String> credentialsProperties) {
        String pathString = credentialsProperties.get("path");
        if (pathString == null) {
            return Result.FAILURE;
        }

        path = Paths.get(pathString);

        if (!Files.exists(path)) {
            return Result.ERROR;
        }

        return Result.SUCCESS;
    }

    public void setPath(Path path) {
        super.setProperty("path", path.toString());
        this.path = path;
    }

    public void setPath(String pathString) {
        setPath(Paths.get(pathString));
    }
}