package net.absolutioncraft.api.shared.rank;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public enum UserRank {
    OWNER("§c[CREADOR] ", "§c"),
    ADMIN("§b[ADMIN] ", "§c"),
    MOD("§a[MOD] ", "§5"),
    DEVELOPMENT("§2[DEV] ", "§2"),
    BT("§d[C. TEAM] ", "§d"),

    YOUTUBER("§f[YOU§cTUBER] ", "§c"),

    VIP("§a[<3] ", "§a"),
    USER("§7", "§7");

    private String prefix;
    private String color;

    UserRank(String display, String color) {
        this.color = color;
        this.prefix = display;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getColor() {
        return color;
    }
}
