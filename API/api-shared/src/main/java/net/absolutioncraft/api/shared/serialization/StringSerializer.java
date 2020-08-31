package net.absolutioncraft.api.shared.serialization;

import net.absolutioncraft.api.shared.user.model.IUser;

/**
 * <h1>String Serializer</h1>
 * StringSerializer class contains util methods
 * for String management.
 * <p>
 * <b>Note:</b> All methods are static, so the user must not use
 * a default constructor.
 *
 * @author MelonDev
 * @since 0.0.1
 */
public final class StringSerializer {

    /**
     * Capitalize submitted string.
     * @param string A {@link String} index.
     * @return Capitalized {@link String}.
     */
    public static String capitalizeString(String string) {
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

    public static String getSupporterHearts(IUser user) {
        int oneMonth = 2592000;
        int twoMonths = 5184000;
        int rankTime = user.getRankExpiration() - user.getSetRankTime();

        if (rankTime <= oneMonth) return "§a[§" + user.getHeartsColor() + "❤§a] ";
        if (rankTime <= twoMonths) return "§a[§" + user.getHeartsColor() + "❤❤§a] ";

        return "§a[§" + user.getHeartsColor() + "❤❤❤§a] ";
    }
}
