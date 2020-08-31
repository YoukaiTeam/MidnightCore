package net.absolutioncraft.commons.bukkit.user.event;

import lombok.AccessLevel;
import lombok.Getter;
import net.absolutioncraft.api.shared.user.model.IUser;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author MelonDev
 * @since 0.1.0
 */
public final class UserLoadEvent extends Event {

    private static HandlerList handlerList = new HandlerList();
    @Getter(value = AccessLevel.PUBLIC)
    private IUser user;
    @Getter(value = AccessLevel.PUBLIC)
    private Player player;

    public UserLoadEvent(IUser user, Player player) {
        this.user = user;
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
