package net.absolutioncraft.api.shared.online.provider;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public interface OnlineProvider {
    boolean isUserOnline(String username);
    long getOnlinePlayers();

    void setStatus(String username, boolean status);
}
