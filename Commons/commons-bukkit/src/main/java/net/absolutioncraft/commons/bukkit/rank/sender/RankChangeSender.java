package net.absolutioncraft.commons.bukkit.rank.sender;

import com.google.common.util.concurrent.ListenableFuture;
import net.absolutioncraft.api.shared.rank.UserRank;
import net.absolutioncraft.api.shared.user.model.IUser;
import net.absolutioncraft.commons.bukkit.rank.response.RankChangeResponse;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public interface RankChangeSender {
    ListenableFuture<RankChangeResponse> sendRankChange(IUser changer, IUser changed, UserRank rank, int expiration);
}
