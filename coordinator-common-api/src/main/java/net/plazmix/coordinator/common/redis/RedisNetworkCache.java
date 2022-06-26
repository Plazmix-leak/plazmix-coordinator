package net.plazmix.coordinator.common.redis;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import redis.clients.jedis.*;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RedisNetworkCache {
    
    private static int cachesCounter = 0;
    public static RedisNetworkCache newNetworkCache(JedisPoolConfig jedisPoolConfig, RedisCredentials credentials) {
        return new RedisNetworkCache(String.format("NetworkSpace-%d", cachesCounter++), jedisPoolConfig, credentials);
    }

    public static RedisNetworkCache newNetworkCache(RedisCredentials credentials) {
        return RedisNetworkCache.newNetworkCache(new JedisPoolConfig(), credentials);
    }
    
    String namespace;

    JedisPoolConfig jedisPoolConfig;
    RedisCredentials credentials;

    @NonFinal
    JedisPool jedisPool;

    public void enable() {
        
        if (jedisPool == null) {
            jedisPool = new JedisPool(jedisPoolConfig, credentials.host(), credentials.port(), 5000, credentials.password());
        }
    }

    public void disable() {
        
        if (jedisPool != null) {
            jedisPool.close();
        }
    }

    public boolean exists(String key) {
        boolean result;
        
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.exists(namespace + ":" + key);
        }
        
        return result;
    }

    public void setString(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(namespace + ":" + key, value);
        }
    }

    public void setLong(String key, long value) {
        this.setString(key, String.valueOf(value));
    }

    public void setInt(String key, int value) {
        this.setString(key, String.valueOf(value));
    }

    public void setShort(String key, short value) {
        this.setString(key, String.valueOf(value));
    }

    public void setByte(String key, byte value) {
        this.setString(key, String.valueOf(value));
    }

    public void setDouble(String key, double value) {
        this.setString(key, String.valueOf(value));
    }

    public void setFloat(String key, float value) {
        this.setString(key, String.valueOf(value));
    }

    public void setBoolean(String key, boolean value) {
        this.setString(key, String.valueOf(value));
    }

    public void setStringSet(String key, Collection<String> value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.sadd(namespace + ":" + key, value.toArray(new String[0]));
        }
    }

    public void setStringSetAddition(String key, String toAdd) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.sadd(namespace + ":" + key, toAdd);
        }
    }

    public void setStringSetDeletion(String key, String toDelete) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.srem(namespace + ":" + key, toDelete);
        }
    }

    public void setStringSetAdditions(String key, Collection<String> toAdd) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.sadd(namespace + ":" + key, toAdd.toArray(new String[0]));
        }
    }

    public void setStringSetDeletions(String key, Collection<String> toAdd) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.sadd(namespace + ":" + key, toAdd.toArray(new String[0]));
        }
    }

    public void setStringMap(String key, Map<String, String> value) {
        try (Jedis jedis = jedisPool.getResource()) {
            
            Pipeline pipeline = jedis.pipelined();
            value.forEach((key1, value1) -> pipeline.hset(namespace + ":" + key, key1, value1));
            
            pipeline.sync();
        }
    }

    public void setStringMapAddition(String key, String mapKey, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(namespace + ":" + key, mapKey, value);
        }
    }

    public void setStringMapDeletion(String key, String... keys) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hdel(namespace + ":" + key, keys);
        }
    }

    public String getString(String key) {
        String result;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.get(namespace + ":" + key);
        }

        return result;
    }

    public long getLong(String key) {
        return Long.parseLong(getString(key));
    }

    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    public short getShort(String key) {
        return Short.parseShort(getString(key));
    }

    public byte getByte(String key) {
        return Byte.parseByte(getString(key));
    }

    public double getDouble(String key) {
        return Double.parseDouble(getString(key));
    }

    public float getFloat(String key) {
        return Float.parseFloat(getString(key));
    }

    public boolean getBoolean(String key) {
        return getString(key).equals("true");
    }

    public Set<String> getStringSet(String key) {
        Set<String> result;

        try (Jedis jedis = jedisPool.getResource()) {
            result = new HashSet<>(jedis.smembers(namespace + ":" + key));
        }

        return result;
    }

    public Map<String, String> getStringMap(String key) {
        Map<String, String> result;

        try (Jedis jedis = jedisPool.getResource()) {
            result = new HashMap<>(jedis.hgetAll(namespace + ":" + key));
        }

        return result;
    }

    public Map<String, String> getMatchingStrings(String match) {
        Map<String, String> result = new HashMap<>();
        Set<String> matchingKeys = new HashSet<>();

        ScanParams params = new ScanParams();
        params.match(namespace + ":" + match);

        try (Jedis jedis = jedisPool.getResource()) {
            String nextCursor = "0";

            do {
                ScanResult<String> scanResult = jedis.scan(nextCursor, params);
                List<String> keys = scanResult.getResult();
                
                nextCursor = scanResult.getCursor();

                matchingKeys.addAll(keys);
            } while (!nextCursor.equals("0"));

            Pipeline pipeline = jedis.pipelined();

            for (String key : matchingKeys) {
                result.put(key, pipeline.get(key).get());
            }

            pipeline.sync();
        }

        return result;
    }

    private <R> Map<String, R> getMatching0(String match, Function<String, R> converter) {
        Map<String, R> result = new HashMap<>();
        Map<String, String> stringMap = getMatchingStrings(match);

        for (Entry<String, String> entry : stringMap.entrySet()) {
            result.put(entry.getKey(), converter.apply(entry.getValue()));
        }

        return result;
    }

    public Map<String, Long> getMatchingLongs(String match) {
        return getMatching0(match, Long::parseLong);
    }

    public Map<String, Integer> getMatchingInts(String match) {
        return getMatching0(match, Integer::parseInt);
    }

    public Map<String, Short> getMatchingShorts(String match) {
        return getMatching0(match, Short::parseShort);
    }

    public Map<String, Byte> getMatchingBytes(String match) {
        return getMatching0(match, Byte::parseByte);
    }

    public Map<String, Double> getMatchingDoubles(String match) {
        return getMatching0(match, Double::parseDouble);
    }

    public Map<String, Float> getMatchingFloats(String match) {
        return getMatching0(match, Float::parseFloat);
    }

    public Map<String, Boolean> getMatchingBooleans(String match) {
        return getMatching0(match, Boolean::parseBoolean);
    }

    public SetMultimap<String, String> getMatchingStringSets(String match) {
        SetMultimap<String, String> result = new HashMultimap<>();
        Collection<String> matchingKeys = new HashSet<>();

        ScanParams params = new ScanParams();
        params.match(namespace + ":" + match);

        try (Jedis jedis = jedisPool.getResource()) {
            String nextCursor = "0";

            do {
                ScanResult<String> scanResult = jedis.scan(nextCursor, params);
                List<String> keys = scanResult.getResult();

                nextCursor = scanResult.getCursor();

                matchingKeys.addAll(keys);
            } while (!nextCursor.equals("0"));

            Pipeline pipeline = jedis.pipelined();

            for (String key : matchingKeys) {
                result.putAll(key, pipeline.smembers(key).get());
            }

            pipeline.sync();
        }

        return result;
    }

    public Map<String, Map<String, String>> getMatchingStringMaps(String match) {
        Map<String, Map<String, String>> result = new HashMap<>();
        Set<String> matchingKeys = new HashSet<>();

        ScanParams params = new ScanParams();
        params.match(namespace + ":" + match);

        try (Jedis jedis = jedisPool.getResource()) {
            String nextCursor = "0";

            do {
                ScanResult<String> scanResult = jedis.scan(nextCursor, params);
                List<String> keys = scanResult.getResult();

                nextCursor = scanResult.getCursor();

                matchingKeys.addAll(keys);
            } while (!nextCursor.equals("0"));

            Pipeline pipeline = jedis.pipelined();
            Map<String, Response<Map<String, String>>> responses = new HashMap<>();

            for (String key : matchingKeys) {
                responses.put(key, pipeline.hgetAll(key));
            }

            pipeline.sync();
            for (Entry<String, Response<Map<String, String>>> entry : responses.entrySet()) {
                result.put(entry.getKey(), entry.getValue().get());
            }
        }

        return result;
    }

    public void remove(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(namespace + ":" + key);
        }
    }

    public void removeMatchingKeys(String match) {
        Set<String> matchingKeys = new HashSet<>();

        ScanParams params = new ScanParams();
        params.match(namespace + ":" + match);

        try (Jedis jedis = jedisPool.getResource()) {
            String nextCursor = "0";

            do {
                ScanResult<String> scanResult = jedis.scan(nextCursor, params);
                List<String> keys = scanResult.getResult();

                nextCursor = scanResult.getCursor();

                matchingKeys.addAll(keys);
            } while (!nextCursor.equals("0"));

            Pipeline pipeline = jedis.pipelined();

            for (String key : matchingKeys) {
                pipeline.del(key);
            }
        }
    }

    public void removeAll() {
        Set<String> matchingKeys = new HashSet<>();

        ScanParams params = new ScanParams();
        params.match(namespace + ":*");

        try (Jedis jedis = jedisPool.getResource()) {
            String nextCursor = "0";

            do {
                ScanResult<String> scanResult = jedis.scan(nextCursor, params);
                List<String> keys = scanResult.getResult();

                nextCursor = scanResult.getCursor();

                matchingKeys.addAll(keys);
            } while (!nextCursor.equals("0"));

            Pipeline pipeline = jedis.pipelined();

            for (String key : matchingKeys) {
                pipeline.del(key);
            }
        }
    }
    
}