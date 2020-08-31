package net.absolutioncraft.commons.bukkit.rank.changeable;

import net.absolutioncraft.api.shared.rank.UserRank;
import net.absolutioncraft.api.shared.user.model.IUser;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public interface ChangedRank {
    IUser changerUser();
    IUser changedUser();

    UserRank rank();
    int expiration();
}
