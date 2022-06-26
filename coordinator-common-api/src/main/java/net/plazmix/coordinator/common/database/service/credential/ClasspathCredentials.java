package net.plazmix.coordinator.common.database.service.credential;

import lombok.Getter;
import net.plazmix.coordinator.common.database.service.PropertyCredentials;

import java.net.URL;
import java.util.Map;

@Getter
public class ClasspathCredentials extends PropertyCredentials {
    private URL classpathUrl;

    @Override
    public boolean validate() {
        return classpathUrl != null;
    }

    @Override
    protected Result init(Map<String, String> credentialsProperties) {
        String urlString = credentialsProperties.get("url");

        if (urlString == null) {
            return Result.FAILURE;
        }

        if ((classpathUrl = ClasspathCredentials.class.getResource(urlString)) == null) {
            return Result.ERROR;
        }

        return Result.SUCCESS;
    }

    public void setClasspathUrl(URL classpathUrl) {
        this.classpathUrl = classpathUrl;
    }

    public void setClasspathUrl(String urlString) {
        setClasspathUrl(ClasspathCredentials.class.getResource(urlString));
    }
}