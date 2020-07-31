package net.absolutioncraft.api.shared.serialization;

import org.ocpsoft.prettytime.PrettyTime;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * <h1>Time Serializer</h1>
 * TimeSerializer class helps the user to beautify time
 * units/stamps to a simplest user-like format.
 * <p>
 * <b>Note:</b> All methods are static, so the user must not use
 * a default constructor.
 *
 * @author MelonDev
 * @since 0.0.1
 */
public final class TimeSerializer {
    private static final Map<String, ChronoUnit> TIME_UNITS;

    static {
        TIME_UNITS = new HashMap<>();
        TIME_UNITS.put("s", ChronoUnit.SECONDS);
        TIME_UNITS.put("S", ChronoUnit.SECONDS);
        TIME_UNITS.put("m", ChronoUnit.MINUTES);
        TIME_UNITS.put("h", ChronoUnit.HOURS);
        TIME_UNITS.put("H", ChronoUnit.HOURS);
        TIME_UNITS.put("d", ChronoUnit.DAYS);
        TIME_UNITS.put("D", ChronoUnit.DAYS);
        TIME_UNITS.put("w", ChronoUnit.WEEKS);
        TIME_UNITS.put("W", ChronoUnit.WEEKS);
        TIME_UNITS.put("M", ChronoUnit.MONTHS);
        TIME_UNITS.put("y", ChronoUnit.YEARS);
        TIME_UNITS.put("Y", ChronoUnit.YEARS);
    }

    /**
     * This method is used to parse a {@link String} based time duration to
     * a milliseconds formatted {@link Long}.
     * @param stringDuration String-based time unit (Ex: 1h, 2m, 50s).
     * @return long Returns parsed string duration in millis.
     */
    public static long parseDuration(String stringDuration){
        long sum = 0;
        StringBuilder number = new StringBuilder();

        for (final char character : stringDuration.toCharArray()) {
            if (Character.isDigit(character)){
                number.append(character);
            } else {
                if (TIME_UNITS.containsKey(character + "") && (number.length() > 0)) {
                    long parsedLong = Long.parseLong(number.toString());
                    ChronoUnit unit = TIME_UNITS.get(character + "");
                    sum += unit.getDuration().multipliedBy(parsedLong).toMillis();
                    number = new StringBuilder();
                }
            }
        }
        return sum;
    }

    /**
     * Use this method to add minutes to a {@link Date}
     * object.
     * @param date A {@link Date} formatted time.
     * @param minutes {@link Integer} minutes to add
     * @return Date with added minutes.
     */
    public static Date addMinutes(Date date, Integer minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * This method adds an "ago" format to the submitted
     * {@link Date} format using {@link Locale} locales.
     * @param date A {@link Date} formatted time.
     * @param locale A {@link Locale} i18n formatted locale {@link String}.
     * @return An ago formatted {@link String} date.
     */
    public static String formatTimeAgo(Date date, String locale) {
        return StringSerializer.capitalizeString(new PrettyTime(new Locale(locale)).format(date));
    }

    /**
     * This method adds an "ago" format to the submitted
     * {@link Date} format using {@link Locale} locales.
     * @param stamp A {@link Integer} timestamp.
     * @param locale A {@link Locale} i18n formatted locale {@link String}.
     * @return An ago formatted {@link String} date.
     */
    public static String formatTimeAgo(Integer stamp, String locale) {
        return StringSerializer.capitalizeString(new PrettyTime(new Locale(locale)).format(new Date(stamp * 1000L)));
    }

    /**
     * Method to convert {@link ZonedDateTime} to Unix
     * like timestamp.
     * @param date A {@link ZonedDateTime} formatted time unit.
     * @return Unix-formatted timestamp {@link Integer}.
     */
    public static Integer getUnixStamp(ZonedDateTime date) {
        return getUnixStamp(Date.from(date.toInstant()));
    }

    /**
     * Method to convert {@link Date} to Unix
     * like timestamp.
     * @param date A {@link Date} formatted time unit.
     * @return Unix-formatted timestamp {@link Integer}.
     */
    public static Integer getUnixStamp(Date date) {
        return (int) (date.getTime() / 1000);
    }

    /**
     * Method to convert {@link Integer} Unix timestamp
     * to {@link Date} date.
     * @param stamp A {@link Integer} formatted time unit.
     * @return {@link Date} formatted date.
     */
    public static Date parseUnixStamp(Integer stamp) {
        return new Date(stamp * 1000L);
    }
}
