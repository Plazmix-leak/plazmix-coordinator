package net.plazmix.coordinator.common.database.service.credential;

import lombok.Getter;
import net.plazmix.coordinator.common.database.service.PropertyCredentials;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Getter
public class WebURLCredentials extends PropertyCredentials {
    private URL webUrl;

    @Override
    public boolean validate() {
        return webUrl != null;
    }

    @Override
    protected Result init(Map<String, String> credentialsProperties) {
        String urlString = credentialsProperties.get("url");

        if (urlString == null) {
            return Result.FAILURE;
        }

        try {
            new URL(urlString).openConnection();
            return Result.SUCCESS;
        }
        catch (Exception exception) {
            return Result.ERROR;
        }
    }

    public void setWebUrl(URL webUrl) {
        super.setProperty("url", webUrl.toString());
        this.webUrl = webUrl;
    }

    public void setWebUrl(String urlString) {
        try {
            setWebUrl(new URL(urlString));
        }
        catch (MalformedURLException exception) {
            exception.printStackTrace();
        }
    }
}