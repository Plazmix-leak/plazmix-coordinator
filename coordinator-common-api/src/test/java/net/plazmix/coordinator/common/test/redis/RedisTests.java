package net.plazmix.coordinator.common.test.redis;

import net.plazmix.coordinator.common.redis.RedisCredentials;
import net.plazmix.coordinator.common.redis.RedisNetworkCache;

public class RedisTests {

    public static void main(String[] args) {
        RedisNetworkCache networkCache = RedisNetworkCache.newNetworkCache(
                RedisCredentials.create(9051, "127.0.0.1", "123qwe")
        );

        networkCache.enable();

        networkCache.setString("serverName", "Plazmix Network");
        networkCache.setInt("globalOnline", 12_647);

        String serverName = networkCache.getString("serverName");
        int globalOnline = networkCache.getInt("globalOnline");

        System.out.println(serverName);
        System.out.println(globalOnline);
    }
}
