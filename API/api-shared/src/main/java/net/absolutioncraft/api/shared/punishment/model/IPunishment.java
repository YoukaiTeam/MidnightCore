package net.absolutioncraft.api.shared.punishment.model;

import net.absolutioncraft.api.shared.punishment.type.PunishmentType;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public interface IPunishment {
    Integer getId();
    PunishmentType getType();

    String getPunisherDisplay();
    String getPunishedDisplay();

    String getOriginServer();
    String getLastIp();
    String getLastMatch();

    String getReason();

    long getUnixExpiration();
    long getUnixCreatedAt();

    boolean isAppealed();
    boolean isSilent();
    boolean isActive();

    void setActive(boolean active);

    default boolean isPermanent() {
        return getUnixExpiration() < 0;
    }
}
