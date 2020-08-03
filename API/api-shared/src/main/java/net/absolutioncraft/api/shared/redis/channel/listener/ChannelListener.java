package net.absolutioncraft.api.shared.redis.channel.listener;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public interface ChannelListener<O> {
    void receiveMessage(O object);
}
