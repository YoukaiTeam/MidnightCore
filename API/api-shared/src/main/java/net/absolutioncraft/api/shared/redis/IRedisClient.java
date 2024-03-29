package net.absolutioncraft.api.shared.redis;

import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Set;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public interface IRedisClient {
    JedisPool getPool();

    String getString(String key);

    String getLastStringElement(String prefix);

    Map<String, String> getHashFields(String key);

    void setString(String key, String value);

    void deleteString(String key);

    void setLastStringElement(String prefix);

    void setExpiration(String key, Integer seconds);

    void setHash(String key, String field, String value);

    void deleteHash(String key, String field);

    Boolean existsKey(String key);

    Set<String> getKeys(String pattern);

    long getExpiringTime(String key);
}
