package io.github.ashishnitw.common;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Utility class for exception handling operations.
 * This class provides static methods for extracting information from exceptions
 * including stack traces and root causes.
 * 
 * @author ashishnitw
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ExceptionUtils {

    /**
     * Private constructor to prevent instantiation.
     */
    private ExceptionUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Gets the stack trace of a throwable as a string.
     *
     * @param throwable the throwable to get the stack trace from
     * @return the stack trace as a string
     * @throws IllegalArgumentException if throwable is null
     */
    public static String getStackTraceAsString(Throwable throwable) {
        if (throwable == null) {
            throw new IllegalArgumentException("Throwable cannot be null");
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * Gets the root cause of a throwable by traversing the cause chain.
     *
     * @param throwable the throwable to get the root cause from
     * @return the root cause throwable
     * @throws IllegalArgumentException if throwable is null
     */
    public static Throwable getRootCause(Throwable throwable) {
        if (throwable == null) {
            throw new IllegalArgumentException("Throwable cannot be null");
        }
        Throwable cause = throwable;
        while (cause.getCause() != null && cause.getCause() != cause) {
            cause = cause.getCause();
        }
        return cause;
    }

    /**
     * Gets the message of the root cause of a throwable.
     *
     * @param throwable the throwable to get the root cause message from
     * @return the root cause message, or null if the root cause has no message
     * @throws IllegalArgumentException if throwable is null
     */
    public static String getRootCauseMessage(Throwable throwable) {
        if (throwable == null) {
            throw new IllegalArgumentException("Throwable cannot be null");
        }
        Throwable rootCause = getRootCause(throwable);
        return rootCause.getMessage();
    }
}
