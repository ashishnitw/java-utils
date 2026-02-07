package io.github.ashishnitw.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for common collection operations.
 * This class provides static methods for collection handling including
 * validation, conversion, and retrieval operations.
 * 
 * @author ashishnitw
 * @version 1.0.0
 * @since 1.0.0
 */
public final class CollectionUtils {

    /**
     * Private constructor to prevent instantiation.
     */
    private CollectionUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Checks if a collection is null or empty.
     *
     * @param collection the collection to check
     * @return true if the collection is null or empty, false otherwise
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Checks if a collection is not empty (not null and contains elements).
     *
     * @param collection the collection to check
     * @return true if the collection is not empty, false otherwise
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    /**
     * Returns the input list if not null, otherwise returns an empty list.
     *
     * @param <T> the type of elements in the list
     * @param list the list to check
     * @return the input list if not null, otherwise an empty list
     */
    public static <T> List<T> safeList(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }

    /**
     * Gets the first element of a list or null if the list is empty or null.
     *
     * @param <T> the type of elements in the list
     * @param list the list to get the first element from
     * @return the first element or null
     */
    public static <T> T firstOrNull(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }
}
