package net.absolutioncraft.api.shared.redis.messager;

import com.google.gson.reflect.TypeToken;

import net.absolutioncraft.api.shared.redis.channel.Channel;

/**
 * @author MelonDev
 * @since 1.0.0
 */

public interface Messager {
    <O> Channel<O> getChannel(String name, TypeToken<O> type);

    default <O> Channel<O> getChannel(String name, Class<O> type) {
        return getChannel(name, TypeToken.get(type));
    }
}
