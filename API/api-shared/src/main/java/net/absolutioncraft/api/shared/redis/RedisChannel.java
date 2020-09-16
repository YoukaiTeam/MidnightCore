package net.absolutioncraft.api.shared.redis;

import net.absolutioncraft.api.shared.redis.channel.Channel;
import net.absolutioncraft.api.shared.redis.channel.listener.ChannelListener;
import net.absolutioncraft.api.shared.redis.event.RedisMessageEvent;

import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public class RedisChannel<O> implements Channel<O> {
    private String name;

    private Supplier<Jedis> subscriberJedis;
    private Supplier<JedisPool> publisherRedis;
    private JedisPubSub pubSub;

    private List<ChannelListener> channelListeners = new ArrayList<>();

    RedisChannel(String name, Supplier<Jedis> subscriberJedis, Supplier<JedisPool> publisherRedis, ExecutorService executorService) {
        this.name = name;

        this.subscriberJedis = subscriberJedis;
        this.publisherRedis = publisherRedis;

        pubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                Bukkit.getServer().getPluginManager().callEvent(new RedisMessageEvent(channel, message));
            }
        };

        executorService.submit(() -> subscriberJedis.get().subscribe(pubSub, name));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void sendMessage(String string) {
        try (Jedis jedis = publisherRedis.get().getResource()) {
            jedis.publish(name, string);
        }
    }

    @Override
    public void registerListener(ChannelListener listener) {
        System.out.println("Listener registrado: " + listener);
        channelListeners.add(listener);
    }
}
