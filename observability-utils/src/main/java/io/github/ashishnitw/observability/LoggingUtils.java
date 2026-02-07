package io.github.ashishnitw.observability;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for logging operations.
 * This class provides static methods for structured logging, debug information,
 * and log level management across the application.
 *
 * @author ashishnitw
 * @version 1.0.0
 * @since 1.0.0
 */
public final class LoggingUtils {

    private static final Logger logger = LoggerFactory.getLogger(LoggingUtils.class);

    /**
     * Private constructor to prevent instantiation.
     */
    private LoggingUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Logs method entry with parameters.
     *
     * @param methodName the name of the method
     * @param params the parameters passed to the method
     */
    public static void logMethodEntry(String methodName, Object... params) {
        if (logger.isTraceEnabled()) {
            StringBuilder sb = new StringBuilder("Entering method: ").append(methodName);
            if (params.length > 0) {
                sb.append(" with parameters: ");
                for (int i = 0; i < params.length; i++) {
                    if (i > 0) sb.append(", ");
                    sb.append(params[i]);
                }
            }
            logger.trace(sb.toString());
        }
    }

    /**
     * Logs method exit with return value.
     *
     * @param methodName the name of the method
     * @param result the return value
     */
    public static void logMethodExit(String methodName, Object result) {
        if (logger.isTraceEnabled()) {
            logger.trace("Exiting method: {} with result: {}", methodName, result);
        }
    }

    /**
     * Logs method execution time.
     *
     * @param methodName the name of the method
     * @param executionTimeMs the execution time in milliseconds
     */
    public static void logMethodExecutionTime(String methodName, long executionTimeMs) {
        if (logger.isDebugEnabled()) {
            logger.debug("Method: {} executed in {} ms", methodName, executionTimeMs);
        }
    }

    /**
     * Logs a debug information about an object state.
     *
     * @param objectName the name of the object
     * @param state the state information
     */
    public static void logObjectState(String objectName, String state) {
        if (logger.isDebugEnabled()) {
            logger.debug("Object state - {}: {}", objectName, state);
        }
    }

    /**
     * Logs performance warning if execution time exceeds threshold.
     *
     * @param methodName the name of the method
     * @param executionTimeMs the execution time in milliseconds
     * @param thresholdMs the threshold in milliseconds
     */
    public static void logPerformanceWarning(String methodName, long executionTimeMs, long thresholdMs) {
        if (executionTimeMs > thresholdMs) {
            logger.warn("Performance warning - Method: {} took {} ms (threshold: {} ms)", 
                    methodName, executionTimeMs, thresholdMs);
        }
    }

    /**
     * Logs an exception with context information.
     *
     * @param message the context message
     * @param exception the exception to log
     */
    public static void logException(String message, Exception exception) {
        logger.error(message, exception);
    }

    /**
     * Logs a warning message with context.
     *
     * @param message the warning message
     * @param context the context information
     */
    public static void logWarning(String message, Object context) {
        if (logger.isWarnEnabled()) {
            logger.warn("{} - Context: {}", message, context);
        }
    }

    /**
     * Logs application startup information.
     *
     * @param appName the name of the application
     * @param version the application version
     */
    public static void logApplicationStartup(String appName, String version) {
        logger.info("========================================");
        logger.info("Application: {} (v{})", appName, version);
        logger.info("Startup completed successfully");
        logger.info("========================================");
    }

    /**
     * Logs application shutdown information.
     *
     * @param appName the name of the application
     */
    public static void logApplicationShutdown(String appName) {
        logger.info("========================================");
        logger.info("Application: {} is shutting down", appName);
        logger.info("========================================");
    }

    /**
     * Gets a logger for a specific class.
     *
     * @param clazz the class
     * @return a logger instance for the class
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * Gets a logger for a specific name.
     *
     * @param loggerName the logger name
     * @return a logger instance with the specified name
     */
    public static Logger getLogger(String loggerName) {
        return LoggerFactory.getLogger(loggerName);
    }
}
