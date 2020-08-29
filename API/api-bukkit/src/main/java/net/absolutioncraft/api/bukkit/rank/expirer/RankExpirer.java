package net.absolutioncraft.api.bukkit.rank.expirer;

import net.absolutioncraft.api.shared.rank.UserRank;
import net.absolutioncraft.api.shared.user.model.IUser;

import java.time.Instant;

/*
 * @author MelonDev
 * @since 1.0.0
 */
public interface RankExpirer {
    /**
     * This method checks if the {@link IUser} {@link UserRank}
     * field has to expire using class {@link Instant#now()} epoch
     * and {@link IUser#getRankExpiration()} epoch times.
     *
     * If the {@link IUser#getRankExpiration()} ()} epoch int is less
     * than {@link Instant#now()} epoch, will call {@link #defaultRank(IUser)}
     *
     * @param user A {@link IUser} not null instance.
     */
    void checkExpiration(IUser user);

    /**
     * Sets the {@link IUser} {@link UserRank} field to
     * {@link UserRank#USER}, then uploads data to the
     * cloud.
     *
     * @param user A {@link IUser} not null instance.
     */
    void defaultRank(IUser user);
}
