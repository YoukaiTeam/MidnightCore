package net.absolutioncraft.api.shared.user.model;

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

    //@NotNull Group getGroup(); // TODO: Make Group objets
    //void setGroup(@NotNull String groupIdentifier);

    @NotNull Date getLastSeen();
    @NotNull String getLastGame();
    @NotNull Date getMemberSince();

    boolean isVerified();
    void setVerified(boolean verified);

    int getLevel();
    void setLevel(int level);

    long getExp();
    void addExp(long exp);
    void removeExp(long exp);

    boolean isAcceptingFriends();
    void setAcceptingFriends(boolean accept);

    boolean isAcceptingParties();
    void setAcceptingParties(boolean accept);

    boolean isShowingStatus();
    void setShowStatus(boolean show);

    boolean isHiding();
    void setHiding(boolean hiding);
}
