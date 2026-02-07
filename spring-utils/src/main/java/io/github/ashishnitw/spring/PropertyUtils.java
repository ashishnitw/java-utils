package io.github.ashishnitw.spring;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for Spring property operations.
 * This class provides static methods for working with application properties,
 * environment variables, and configuration management.
 *
 * @author ashishnitw
 * @version 1.0.0
 * @since 1.0.0
 */
public final class PropertyUtils {

    /**
     * Private constructor to prevent instantiation.
     */
    private PropertyUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Gets a system property value.
     *
     * @param propertyName the name of the property
     * @return the property value, or null if not found
     */
    public static String getSystemProperty(String propertyName) {
        return System.getProperty(propertyName);
    }

    /**
     * Gets an environment variable value.
     *
     * @param variableName the name of the environment variable
     * @return the variable value, or null if not found
     */
    public static String getEnvironmentVariable(String variableName) {
        return System.getenv(variableName);
    }

    /**
     * Gets a system property with a default value if not found.
     *
     * @param propertyName the name of the property
     * @param defaultValue the default value
     * @return the property value, or defaultValue if not found
     */
    public static String getSystemPropertyOrDefault(String propertyName, String defaultValue) {
        String value = getSystemProperty(propertyName);
        return value != null ? value : defaultValue;
    }

    /**
     * Gets an environment variable with a default value if not found.
     *
     * @param variableName the name of the environment variable
     * @param defaultValue the default value
     * @return the variable value, or defaultValue if not found
     */
    public static String getEnvironmentVariableOrDefault(String variableName, String defaultValue) {
        String value = getEnvironmentVariable(variableName);
        return value != null ? value : defaultValue;
    }

    /**
     * Gets all system properties as a map.
     *
     * @return a map of all system properties
     */
    public static Map<String, String> getAllSystemProperties() {
        Map<String, String> properties = new HashMap<>();
        System.getProperties().forEach((key, value) -> 
            properties.put(key.toString(), value.toString())
        );
        return properties;
    }

    /**
     * Gets all environment variables as a map.
     *
     * @return a map of all environment variables
     */
    public static Map<String, String> getAllEnvironmentVariables() {
        return new HashMap<>(System.getenv());
    }

    /**
     * Checks if a system property exists.
     *
     * @param propertyName the name of the property
     * @return true if the property exists, false otherwise
     */
    public static boolean hasSystemProperty(String propertyName) {
        return System.getProperty(propertyName) != null;
    }

    /**
     * Checks if an environment variable exists.
     *
     * @param variableName the name of the environment variable
     * @return true if the variable exists, false otherwise
     */
    public static boolean hasEnvironmentVariable(String variableName) {
        return System.getenv(variableName) != null;
    }

    /**
     * Sets a system property.
     *
     * @param propertyName the name of the property
     * @param value the value to set
     * @return the previous value of the property, or null if there was no previous value
     */
    public static String setSystemProperty(String propertyName, String value) {
        return System.setProperty(propertyName, value);
    }
}
