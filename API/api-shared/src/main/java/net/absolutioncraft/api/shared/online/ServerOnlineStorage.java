package net.absolutioncraft.api.shared.online;

import com.google.inject.Inject;

import net.absolutioncraft.api.shared.online.provider.OnlineProvider;
import net.absolutioncraft.api.shared.redis.RedisClient;

import redis.clients.jedis.Jedis;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class ServerOnlineStorage implements OnlineProvider {
    @Inject private RedisClient redisClient;

    private static final String LOCATION = "online.users";

    @Override
    public boolean isUserOnline(String username) {
        try (Jedis jedis = redisClient.getPool().getResource()) {
            return jedis.sismember(LOCATION, username);
        }
    }

    @Override
    public long getOnlinePlayers() {
        try (Jedis jedis = redisClient.getPool().getResource()) {
            return jedis.llen(LOCATION);
        }
    }

    @Override
    public void setStatus(String username, boolean status) {
        try (Jedis jedis = redisClient.getPool().getResource()) {
            boolean idIsMember = jedis.sismember(LOCATION, username);

            if (status && !idIsMember) {
                jedis.sadd(LOCATION, username);
                return;
            }

            if (!status && idIsMember) {
                jedis.srem(LOCATION, username);
            }
        }
    }
}
