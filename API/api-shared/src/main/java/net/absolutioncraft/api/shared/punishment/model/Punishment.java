package net.absolutioncraft.api.shared.punishment.model;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.absolutioncraft.api.shared.punishment.type.PunishmentType;

/**
 * @author MelonDev
 * @since 0.0.1
 */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public final class Punishment implements IPunishment {
    @SerializedName("id") @NonNull private int id;
    @SerializedName("punishmentType") @NonNull private PunishmentType punishmentType;

    @SerializedName("punisherDisplay") private @NonNull String punisherDisplay;
    @SerializedName("punishedDisplay") private @NonNull String punishedDisplay;

    @SerializedName("originServer") private @NonNull String originServer;
    @SerializedName("lastIp") private @NonNull String lastIp;
    @SerializedName("lastMatch") private String lastMatch;

    @SerializedName("reason") private @NonNull String reason;

    @SerializedName("unixExpiration") private long unixExpiration;
    @SerializedName("unixCreatedAt") private long unixCreatedAt;

    @SerializedName("appealed") private boolean appealed;
    @SerializedName("silent") private boolean silent;
    @SerializedName("active") private boolean active;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public PunishmentType getType() {
        return punishmentType;
    }

    @Override
    public String getPunisherDisplay() {
        return punisherDisplay;
    }

    @Override
    public String getPunishedDisplay() {
        return punishedDisplay;
    }

    @Override
    public String getOriginServer() {
        return originServer;
    }

    @Override
    public String getLastIp() {
        return lastIp;
    }

    @Override
    public String getLastMatch() {
        return lastMatch;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public long getUnixExpiration() {
        return unixExpiration;
    }

    @Override
    public long getUnixCreatedAt() {
        return unixCreatedAt;
    }

    @Override
    public boolean isAppealed() {
        return appealed;
    }

    @Override
    public boolean isSilent() {
        return silent;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
}
