package com.masolria.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * The Date time parse util.
 * Parses LocalDateTime and LocalDate objects from given format String.
 */
public final class DateTimeParseUtil {
    private DateTimeParseUtil(){ }
    /**
     * The Format local date time.
     */
    private static final String FORMAT_LOCAL_DATE_TIME = "yyyy MM dd HH:mm";
    /**
     * The Format local date.
     */
    private static final String FORMAT_LOCAL_DATE = "yyyy MM dd";
    /**
     * The LocalDate formatter.
     */
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(FORMAT_LOCAL_DATE);
    /**
     * The LocalDateTime formatter.
     */
    private static final DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern(FORMAT_LOCAL_DATE_TIME);
    /**
     * Parses to LocalDateTime from the given string, which must match the format.     *
     * @param pattern the pattern with given date and time.
     * @return the LocalDateTime with given date and time.
     */
    public static LocalDateTime parseToLDateTime(String pattern) {
        return LocalDateTime.from(dateTimeformatter.parse(pattern));
    }
    /**
     * Parse to localDate from the given string, which must match the format.
     *
     * @param pattern the pattern with given date.
     * @return the LocalDate object with given date.
     */
    public static LocalDate parseToLDate(String pattern) {
        return LocalDate.from(dateFormatter.parse(pattern));
    }
}