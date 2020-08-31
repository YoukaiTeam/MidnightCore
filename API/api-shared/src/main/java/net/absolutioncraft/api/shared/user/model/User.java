package net.absolutioncraft.api.shared.user.model;

import com.google.gson.annotations.SerializedName;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import net.absolutioncraft.api.shared.rank.UserRank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * @author MelonDev
 * @since 0.0.1
 */

@AllArgsConstructor(access = AccessLevel.PUBLIC)
public final class User implements IUser {
    @SerializedName("uuid") private @NotNull String uuid;
    @SerializedName("username") private @NotNull String username;
    @SerializedName("displayName") private @NotNull String displayName;
    @SerializedName("email") private @Nullable String email;

    @SerializedName("userRank") private UserRank userRank;
    @SerializedName("setRankTime") private Integer setRankTime;
    @SerializedName("rankExpiration") private Integer rankExpiration;

    @SerializedName("heartsColor") private String heartsColor;

    @SerializedName("lastSeen") private long lastSeen;
    @SerializedName("lastGame") private @NotNull String lastGame;
    @SerializedName("memberSince") private long memberSince;

    @SerializedName("verified") private int verified;
    @SerializedName("level") private int level;
    @SerializedName("exp") private long exp;

    @SerializedName("acceptFriends") private int acceptFriends;
    @SerializedName("acceptParties") private int acceptParties;
    @SerializedName("showStatus") private int showStatus;
    @SerializedName("hiding") private int hiding;

    @Override
    public @NotNull String getUniqueId() {
        return this.uuid;
    }

    @Override
    public @NotNull String getUsername() {
        return this.username;
    }

    @Override
    public @NotNull String getDisplayName() {
        return this.displayName;
    }

    @Override
    public @Nullable String getEmail() {
        return this.email;
    }

    @Override
    public @NotNull UserRank getUserRank() {
        return userRank;
    }

    @Override
    public void setUserRank(@NotNull String groupIdentifier) {
        this.userRank = UserRank.valueOf(groupIdentifier);
    }

    @Override
    public @NotNull Integer getSetRankTime() {
        return setRankTime;
    }

    @Override
    public void setSetRankTime(@NotNull Integer unixTime) {
        this.setRankTime = unixTime;
    }

    @Override
    public @NotNull Integer getRankExpiration() {
        return rankExpiration;
    }

    @Override
    public void setRankExpiration(@NotNull Integer unixTime) {
        this.rankExpiration = unixTime;
    }

    @Override
    public @NotNull String getHeartsColor() {
        return heartsColor;
    }

    @Override
    public void setHeartsColor(String string) {
        this.heartsColor = string;
    }

    @Override
    public @NotNull Integer getLastSeen() {
        return (int) this.lastSeen;
    }

    @Override
    public @NotNull String getLastGame() {
        return this.lastGame;
    }

    @Override
    public @NotNull Integer getMemberSince() {
        return (int) this.memberSince;
    }

    @Override
    public int isVerified() {
        return this.verified;
    }

    @Override
    public void setVerified(int verified) {
        this.verified = verified;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public long getExp() {
        return this.exp;
    }

    @Override
    public void addExp(long experience) {
        this.exp += experience;
    }

    @Override
    public void removeExp(long experience) {
        this.exp -= experience;
    }

    @Override
    public int isAcceptingFriends() {
        return this.acceptFriends;
    }

    @Override
    public void setAcceptingFriends(int accept) {
        this.acceptFriends = accept;
    }

    @Override
    public int isAcceptingParties() {
        return acceptParties;
    }

    @Override
    public void setAcceptingParties(int accept) {
        this.acceptParties = accept;
    }

    @Override
    public int isShowingStatus() {
        return showStatus;
    }

    @Override
    public void setShowStatus(int accept) {
        this.showStatus = accept;
    }

    @Override
    public int isHiding() {
        return this.hiding;
    }

    @Override
    public void setHiding(int hiding) {
        this.hiding = hiding;
    }
}
