package net.plazmix.coordinator.common.redis;

public record RedisCredentials(String host, String password, int port) {

    public static RedisCredentials create(int port, String host, String password) {
        return new RedisCredentials(host, password, port);
    }
}
