package net.absolutioncraft.api.shared.redis;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import net.absolutioncraft.api.shared.redis.channel.Channel;
import net.absolutioncraft.api.shared.redis.config.RedisClientConfiguration;
import net.absolutioncraft.api.shared.redis.messager.Messager;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author MelonDev
 * @since 0.0.1
 */
@Singleton
public class RedisMessager implements Messager {

    private Lock lock;

    private static Map<String, Channel> registeredChannels = new HashMap<>();

    private RedisClientConfiguration configuration;

    private Jedis jedis;
    private JedisPool jedisPool;

    private ExecutorService executorService;

    @Inject
    RedisMessager(RedisClientConfiguration configuration, ExecutorService executorService) {
        this.lock = new ReentrantLock();

        this.configuration = configuration;

        this.executorService = executorService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <O> Channel<O> getChannel(String name) {
        try {
            lock.lock();

            if (registeredChannels.containsKey(name)) {
                return registeredChannels.get(name);
            }

            Channel<O> channel = new RedisChannel<>(name, this::getConnection, this::getPool, executorService);

            registeredChannels.put(name, channel);
            return channel;
        } finally {
            lock.unlock();
        }
    }

    Jedis getConnection() {
        lock.lock();

        try {
            if (jedis == null) {
                jedis = new Jedis(configuration.getAddress(), configuration.getPort());
            }

            return jedis;
        } finally {
            lock.unlock();
        }
    }

    JedisPool getPool() {
        if (this.jedisPool == null) {
            this.jedisPool = new JedisPool(this.configuration.getAddress(), this.configuration.getPort());
        }
        return jedisPool;
    }
}
