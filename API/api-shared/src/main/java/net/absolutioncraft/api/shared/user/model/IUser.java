package net.absolutioncraft.api.shared.user.model;

import net.absolutioncraft.api.shared.rank.UserRank;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public interface IUser {
    @NotNull String getUniqueId();
    @NotNull String getUsername();
    @NotNull String getDisplayName();
    @Nullable String getEmail();

    @NotNull UserRank getUserRank();
    void setUserRank(@NotNull String groupIdentifier);
    @NotNull Integer getSetRankTime();
    void setSetRankTime(@NotNull Integer unixTime);
    @NotNull Integer getRankExpiration();
    void setRankExpiration(@NotNull Integer unixTime);

    @NotNull Integer getLastSeen();
    @NotNull String getLastGame();
    @NotNull Integer getMemberSince();

    int isVerified();
    void setVerified(int verified);

    int getLevel();
    void setLevel(int level);

    long getExp();
    void addExp(long exp);
    void removeExp(long exp);

    int isAcceptingFriends();
    void setAcceptingFriends(int accept);

    int isAcceptingParties();
    void setAcceptingParties(int accept);

    int isShowingStatus();
    void setShowStatus(int show);

    int isHiding();
    void setHiding(int hiding);
}
