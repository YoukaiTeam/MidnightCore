package net.absolutioncraft.api.bukkit.board.util;

import org.bukkit.ChatColor;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class StringSplitter {
    public static String genEntry(int slot) {
        return ChatColor.values()[slot].toString();
    }

    public static String getFirstSplit(String s) {
        return s.length() > 16 ? s.substring(0, 16) : s;
    }

    public static String getSecondSplit(String s) {
        if (s.length() > 32) {
            s = s.substring(0, 32);
        }
        return s.length() > 16 ? s.substring(16) : "";
    }
}
