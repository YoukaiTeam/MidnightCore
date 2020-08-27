package net.absolutioncraft.api.shared.redis;

import com.google.inject.Inject;

import net.absolutioncraft.api.shared.redis.config.RedisClientConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Set;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public class RedisClient implements IRedisClient {

    private final RedisClientConfiguration config;
    private JedisPool pool;

    @Inject
    RedisClient(RedisClientConfiguration config) {
        this.config = config;
    }

    public JedisPool getPool() {
        if (this.pool == null) {
            this.pool = new JedisPool(this.config.getAddress(), this.config.getPort());
        }
        this.ping();
        return pool;
    }

    public void ping() {
        try (Jedis client = this.pool.getResource()) {
            client.ping();
        }
    }

    public void setString(String key, String value) {
        try (Jedis client = getPool().getResource()) {
            //TODO: Fix password/database issue
            /*client.auth(this.config.getPassword());
            client.select(this.config.getDatabase());*/
            client.set(key, value);
        }
    }

    public void deleteString(String key) {
        try (Jedis client = getPool().getResource()) {
            client.del(key);
        }
    }

    public String getLastStringElement(String key) {
        try (Jedis client = getPool().getResource()) {
            return client.lpop(key);
        }
    }

    public Map<String, String> getHashFields(String key) {
        try (Jedis client = getPool().getResource()) {
            return client.hgetAll(key);
        }
    }

    public String getString(String key) {
        try (Jedis client = getPool().getResource()) {
            return client.get(key);
        }
    }

    public void setLastStringElement(String key) {
        try (Jedis client = getPool().getResource()) {
            client.lpush(key);
        }
    }

    public void setHash(String key, String field, String value) {
        try (Jedis client = getPool().getResource()) {
            client.hset(key, field, value);
        }
    }

    public void deleteHash(String key, String field) {
        try (Jedis client = getPool().getResource()) {
            client.hdel(key, field);
        }
    }

    public void setExpiration(String key, Integer seconds) {
        try (Jedis client = getPool().getResource()) {
            client.expire(key, seconds);
        }
    }

    public void setExpiration(String key, long seconds) {
        try (Jedis client = getPool().getResource()) {
            client.expire(key, (int) seconds);
        }
    }

    public Boolean existsKey(String key) {
        try (Jedis client = getPool().getResource()) {
            return client.exists(key);
        }
    }

    public Set<String> getKeys(String pattern) {
        try (Jedis client = getPool().getResource()) {
            return client.keys(pattern);
        }
    }

    public long getExpiringTime(String key) {
        try (Jedis client = getPool().getResource()) {
            return client.ttl(key);
        }
    }
}
