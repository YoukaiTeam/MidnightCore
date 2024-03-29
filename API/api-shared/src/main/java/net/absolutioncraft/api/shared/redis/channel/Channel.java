package net.absolutioncraft.api.shared.redis.channel;

import com.google.gson.reflect.TypeToken;
import net.absolutioncraft.api.shared.redis.channel.listener.ChannelListener;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public interface Channel<O> {
    String getName();

    void sendMessage(String string);

    void registerListener(ChannelListener listener);
}
