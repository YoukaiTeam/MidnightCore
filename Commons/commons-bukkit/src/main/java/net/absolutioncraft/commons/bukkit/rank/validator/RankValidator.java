package net.absolutioncraft.commons.bukkit.rank.validator;

import net.absolutioncraft.api.shared.rank.UserRank;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class RankValidator {
    public static boolean isValid(String rankName) {
        for (UserRank ranks : UserRank.values()) {
            if (ranks.name().equals(rankName)) {
                return true;
            }
        }
        return false;
    }
}
