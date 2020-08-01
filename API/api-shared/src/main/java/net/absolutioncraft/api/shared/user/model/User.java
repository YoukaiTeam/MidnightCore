package net.absolutioncraft.api.shared.user.model;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import net.absolutioncraft.api.shared.serialization.TimeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

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

    @SerializedName("lastSeen") private long lastSeen;
    @SerializedName("lastGame") private @NotNull String lastGame;
    @SerializedName("memberSince") private long memberSince;

    @SerializedName("verified") private boolean verified;
    @SerializedName("level") private int level;
    @SerializedName("exp") private long exp;

    @SerializedName("acceptFriends") private boolean acceptFriends;
    @SerializedName("acceptParties") private boolean acceptParties;
    @SerializedName("showStatus") private boolean showStatus;
    @SerializedName("hiding") private boolean hiding;

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
    public @NotNull Date getLastSeen() {
        return TimeSerializer.parseUnixStamp((int) this.lastSeen);
    }

    @Override
    public @NotNull String getLastGame() {
        return this.lastGame;
    }

    @Override
    public @NotNull Date getMemberSince() {
        return TimeSerializer.parseUnixStamp((int) this.memberSince);
    }

    @Override
    public boolean isVerified() {
        return this.verified;
    }

    @Override
    public void setVerified(boolean verified) {
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
    public boolean isAcceptingFriends() {
        return this.acceptFriends;
    }

    @Override
    public void setAcceptingFriends(boolean accept) {
        this.acceptFriends = accept;
    }

    @Override
    public boolean isAcceptingParties() {
        return acceptParties;
    }

    @Override
    public void setAcceptingParties(boolean accept) {
        this.acceptParties = accept;
    }

    @Override
    public boolean isShowingStatus() {
        return showStatus;
    }

    @Override
    public void setShowStatus(boolean accept) {
        this.showStatus = accept;
    }

    @Override
    public boolean isHiding() {
        return this.hiding;
    }

    @Override
    public void setHiding(boolean hiding) {
        this.hiding = hiding;
    }
}
