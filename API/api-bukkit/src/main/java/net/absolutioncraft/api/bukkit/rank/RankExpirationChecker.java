package net.absolutioncraft.api.bukkit.rank;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import net.absolutioncraft.api.bukkit.rank.expirer.RankExpirer;
import net.absolutioncraft.api.bukkit.storage.provider.UserDataProvider;
import net.absolutioncraft.api.shared.http.exception.InternalServerErrorException;
import net.absolutioncraft.api.shared.http.exception.NotFoundException;
import net.absolutioncraft.api.shared.http.exception.ServiceUnavailableException;
import net.absolutioncraft.api.shared.rank.UserRank;
import net.absolutioncraft.api.shared.user.model.IUser;
import org.bukkit.Bukkit;

import java.time.Instant;
import java.util.logging.Level;

/**
 * @author MelonDev
 * @since 1.0.0
 */
@Singleton
public final class RankExpirationChecker implements RankExpirer {
    @Inject private UserDataProvider userDataProvider;

    @Override
    public void checkExpiration(IUser user) {
        int nowEpoch = Math.toIntExact(Instant.now().getEpochSecond());
        int expirationEpoch = user.getRankExpiration();
        if (expirationEpoch == -1 || expirationEpoch == 0) return;
        if (nowEpoch > expirationEpoch) {
            defaultRank(user);
        }
    }

    @Override
    public void defaultRank(IUser user) {
        user.setUserRank(UserRank.USER.toString());
        try {
            userDataProvider.updateUserData(user);
        } catch (NotFoundException | InternalServerErrorException | ServiceUnavailableException e) {
            e.printStackTrace();
            Bukkit.getLogger().log(Level.SEVERE, "[API] Something went wrong while uploading data with default rank for {0} ({1} - #{2})",
                    new Object[] { user.getDisplayName(), e.getCause(), e.statusCode()});
        }
    }
}
