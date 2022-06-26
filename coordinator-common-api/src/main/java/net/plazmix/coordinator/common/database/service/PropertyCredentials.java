package net.plazmix.coordinator.common.database.service;

import java.util.HashMap;
import java.util.Map;

public abstract class PropertyCredentials {

    public enum Result {
        SUCCESS, FAILURE, ERROR,
    }

    private final Map<String, String> credentialsProperties = new HashMap<>();

    public abstract boolean validate();
    protected abstract Result init(Map<String, String> credentialsProperties);

    public void setProperty(String key, String value) {
        credentialsProperties.put(key, value);
    }

    public void setProperties(PropertyCredentials credentials) {
        credentialsProperties.putAll(credentials.credentialsProperties);
    }

    public void resetProperties() {
        credentialsProperties.clear();
    }

    public final Result join() {
        return init(credentialsProperties);
    }

}
