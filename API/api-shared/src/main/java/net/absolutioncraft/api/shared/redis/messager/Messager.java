package net.absolutioncraft.api.shared.redis.messager;

import net.absolutioncraft.api.shared.redis.channel.Channel;

/**
 * @author MelonDev
 * @since 1.0.0
 */

public interface Messager {
    <O> Channel<O> getChannel(String name);
}
