package com.masolria.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParseUtil {
    static final String FORMAT_LOCAL_DATE_TIME = "yyyy MM dd HH:mm";
    static final String FORMAT_LOCAL_DATE = "yyyy MM dd";
    static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(FORMAT_LOCAL_DATE);
    static final DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern(FORMAT_LOCAL_DATE_TIME);

    public static LocalDateTime parseToLDateTime(String pattern) {
        return LocalDateTime.from(dateTimeformatter.parse(pattern));
    }

    public static LocalDate parseToLDate(String pattern) {
        return LocalDate.from(dateFormatter.parse(pattern));
    }
}
