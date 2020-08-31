package net.absolutioncraft.commons.bukkit.rank;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import net.absolutioncraft.api.shared.rank.UserRank;
import net.absolutioncraft.api.shared.user.model.IUser;
import net.absolutioncraft.commons.bukkit.rank.changeable.ChangedRank;

/**
 * @author MelonDev
 * @since 1.0.0
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public final class RankChange implements ChangedRank {
    @SerializedName("changerUser") private IUser changerUser;
    @SerializedName("changedUser") private IUser changedUser;
    @SerializedName("rank") private UserRank rank;
    @SerializedName("expiration") private int expiration;

    @Override
    public IUser changerUser() {
        return changerUser;
    }

    @Override
    public IUser changedUser() {
        return changedUser;
    }

    @Override
    public UserRank rank() {
        return rank;
    }

    @Override
    public int expiration() {
        return expiration;
    }
}
