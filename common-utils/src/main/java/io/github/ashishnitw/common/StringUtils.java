package io.github.ashishnitw.common;

/**
 * Utility class for common string manipulation operations.
 * This class provides static methods for string handling including
 * validation, transformation, and conversion operations.
 * 
 * @author ashishnitw
 * @version 1.0.0
 * @since 1.0.0
 */
public final class StringUtils {

    /**
     * Private constructor to prevent instantiation.
     */
    private StringUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Checks if a string is null or empty.
     *
     * @param str the string to check
     * @return true if the string is null or empty, false otherwise
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Checks if a string is null, empty, or contains only whitespace.
     *
     * @param str the string to check
     * @return true if the string is null, empty, or whitespace, false otherwise
     */
    public static boolean isBlank(String str) {
        return str == null || str.isBlank();
    }

    /**
     * Capitalizes the first letter of a string.
     *
     * @param str the string to capitalize
     * @return the capitalized string, or null if input is null
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Truncates a string to the specified maximum length.
     * If the string is longer than maxLength, it will be truncated.
     *
     * @param str the string to truncate
     * @param maxLength the maximum length
     * @return the truncated string, or null if input is null
     * @throws IllegalArgumentException if maxLength is negative
     */
    public static String truncate(String str, int maxLength) {
        if (maxLength < 0) {
            throw new IllegalArgumentException("maxLength cannot be negative");
        }
        if (str == null) {
            return null;
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength);
    }

    /**
     * Converts a null string to an empty string.
     *
     * @param str the string to convert
     * @return empty string if input is null, otherwise the input string
     */
    public static String nullToEmpty(String str) {
        return str == null ? "" : str;
    }

    /**
     * Converts an empty string to null.
     *
     * @param str the string to convert
     * @return null if input is empty, otherwise the input string
     */
    public static String emptyToNull(String str) {
        return isEmpty(str) ? null : str;
    }
}
