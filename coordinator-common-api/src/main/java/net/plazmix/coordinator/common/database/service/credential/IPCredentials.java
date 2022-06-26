package net.plazmix.coordinator.common.database.service.credential;

import lombok.Getter;
import net.plazmix.coordinator.common.database.service.PropertyCredentials;

import java.net.URL;
import java.util.Map;

@Getter
public class IPCredentials extends PropertyCredentials {

    private String server;
    private int port;

    private String username;
    private String password;

    private String filepath;

    @Override
    public boolean validate() {
        return server != null && port > 0;
    }

    @Override
    protected Result init(Map<String, String> credentialsProperties) {
        server = credentialsProperties.get("server");
        port = Integer.parseInt(credentialsProperties.getOrDefault("port", "0"));

        if (!validate()) {
            return Result.FAILURE;
        }

        username = credentialsProperties.get("username");
        password = credentialsProperties.get("password");

        filepath = credentialsProperties.get("filepath");

        return Result.SUCCESS;
    }

    public void setPort(int port) {
        super.setProperty("port", String.valueOf(port));
        this.port = port;
    }

    public void setUsername(String username) {
        super.setProperty("username", username);
        this.username = username;
    }

    public void setPassword(String password) {
        super.setProperty("password", password);
        this.password = password;
    }

    public void setServer(String server) {
        super.setProperty("server", server);
        this.server = server;
    }

    public void setFilepath(String filepath) {
        super.setProperty("filepath", filepath);
        this.filepath = filepath;
    }

}