package io.github.ashishnitw.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for common date and time operations.
 * This class provides static methods for date/time handling including
 * formatting, parsing, and conversion operations.
 * 
 * @author ashishnitw
 * @version 1.0.0
 * @since 1.0.0
 */
public final class DateTimeUtils {

    /**
     * Private constructor to prevent instantiation.
     */
    private DateTimeUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Gets the current timestamp as LocalDateTime.
     *
     * @return the current LocalDateTime
     */
    public static LocalDateTime getCurrentTimestamp() {
        return LocalDateTime.now();
    }

    /**
     * Formats a LocalDateTime according to the specified pattern.
     *
     * @param date the LocalDateTime to format
     * @param pattern the pattern to use for formatting
     * @return the formatted date string
     * @throws IllegalArgumentException if date or pattern is null
     * @throws DateTimeParseException if the pattern is invalid
     */
    public static String formatDate(LocalDateTime date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    /**
     * Parses a date string according to the specified pattern.
     *
     * @param dateStr the date string to parse
     * @param pattern the pattern to use for parsing
     * @return the parsed LocalDateTime
     * @throws IllegalArgumentException if dateStr or pattern is null
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalDateTime parseDate(String dateStr, String pattern) {
        if (dateStr == null) {
            throw new IllegalArgumentException("Date string cannot be null");
        }
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateStr, formatter);
    }

    /**
     * Converts a LocalDateTime to epoch milliseconds.
     *
     * @param date the LocalDateTime to convert
     * @return the epoch milliseconds
     * @throws IllegalArgumentException if date is null
     */
    public static long toEpochMilli(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * Converts epoch milliseconds to LocalDateTime.
     *
     * @param epochMilli the epoch milliseconds
     * @return the LocalDateTime
     */
    public static LocalDateTime fromEpochMilli(long epochMilli) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneId.systemDefault());
    }
}
