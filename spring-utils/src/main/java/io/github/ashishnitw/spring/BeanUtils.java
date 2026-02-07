package io.github.ashishnitw.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for Spring bean operations.
 * This class provides static methods for working with Spring beans,
 * including property access, bean instantiation helpers, and validation.
 *
 * @author ashishnitw
 * @version 1.0.0
 * @since 1.0.0
 */
public final class BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * Private constructor to prevent instantiation.
     */
    private BeanUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Checks if an object is null.
     *
     * @param obj the object to check
     * @return true if the object is null, false otherwise
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * Checks if an object is not null.
     *
     * @param obj the object to check
     * @return true if the object is not null, false otherwise
     */
    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    /**
     * Gets the simple class name of an object.
     *
     * @param obj the object
     * @return the simple class name, or "null" if obj is null
     */
    public static String getSimpleClassName(Object obj) {
        if (isNull(obj)) {
            return "null";
        }
        return obj.getClass().getSimpleName();
    }

    /**
     * Gets the fully qualified class name of an object.
     *
     * @param obj the object
     * @return the fully qualified class name, or "null" if obj is null
     */
    public static String getFullyQualifiedClassName(Object obj) {
        if (isNull(obj)) {
            return "null";
        }
        return obj.getClass().getName();
    }

    /**
     * Safely casts an object to the specified type.
     *
     * @param <T> the target type
     * @param obj the object to cast
     * @param targetClass the target class
     * @return the casted object, or null if obj is null or not an instance of targetClass
     */
    @SuppressWarnings("unchecked")
    public static <T> T safeCast(Object obj, Class<T> targetClass) {
        if (isNull(obj)) {
            return null;
        }
        if (targetClass.isInstance(obj)) {
            return (T) obj;
        }
        logger.warn("Unable to cast {} to {}", getFullyQualifiedClassName(obj), targetClass.getName());
        return null;
    }

    /**
     * Checks if an object is an instance of the specified class.
     *
     * @param obj the object to check
     * @param targetClass the target class
     * @return true if obj is an instance of targetClass, false otherwise
     */
    public static boolean isInstanceOf(Object obj, Class<?> targetClass) {
        if (isNull(obj) || isNull(targetClass)) {
            return false;
        }
        return targetClass.isInstance(obj);
    }

    /**
     * Returns a default value if the object is null.
     *
     * @param <T> the type of the object
     * @param obj the object to check
     * @param defaultValue the default value to return if obj is null
     * @return the object if not null, otherwise the default value
     */
    public static <T> T getOrDefault(T obj, T defaultValue) {
        return isNull(obj) ? defaultValue : obj;
    }
}
