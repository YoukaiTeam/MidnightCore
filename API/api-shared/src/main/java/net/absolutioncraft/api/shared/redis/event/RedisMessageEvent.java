package net.absolutioncraft.api.shared.redis.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author MelonDev
 * @since 0.0.1
 */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public final class RedisMessageEvent extends Event {
    private static HandlerList handlerList = new HandlerList();
    @Getter private @NonNull String channel;
    @Getter private @NonNull String message;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
