package net.absolutioncraft.commons.bukkit.rank.event;

import lombok.AccessLevel;
import lombok.Getter;
import net.absolutioncraft.api.shared.rank.UserRank;
import net.absolutioncraft.api.shared.user.model.IUser;
import net.absolutioncraft.commons.bukkit.listener.RankRequestListener;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This event will be called only when
 * the {@link RankRequestListener} handles some
 * rank change and the destination user
 * is online.
 *
 * @author MelonDev
 * @since 1.0.0
 */
public final class UserRankChangeEvent extends Event {
    private static HandlerList handlerList = new HandlerList();
    @Getter(value = AccessLevel.PUBLIC)
    private IUser changer;
    @Getter(value = AccessLevel.PUBLIC)
    private IUser changed;
    @Getter(value = AccessLevel.PUBLIC)
    private UserRank destinationRank;

    public UserRankChangeEvent(IUser changer, IUser changed, UserRank destinationRank) {
        this.changer = changer;
        this.changed = changed;
        this.destinationRank = destinationRank;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
