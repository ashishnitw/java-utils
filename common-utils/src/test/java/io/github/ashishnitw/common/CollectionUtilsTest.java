package io.github.ashishnitw.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CollectionUtils.
 */
class CollectionUtilsTest {

    @Test
    @DisplayName("Test isEmpty with null collection")
    void testIsEmpty_Null() {
        assertTrue(CollectionUtils.isEmpty(null));
    }

    @Test
    @DisplayName("Test isEmpty with empty collection")
    void testIsEmpty_Empty() {
        assertTrue(CollectionUtils.isEmpty(new ArrayList<>()));
    }

    @Test
    @DisplayName("Test isEmpty with non-empty collection")
    void testIsEmpty_NonEmpty() {
        assertFalse(CollectionUtils.isEmpty(Arrays.asList("test")));
    }

    @Test
    @DisplayName("Test isNotEmpty with null collection")
    void testIsNotEmpty_Null() {
        assertFalse(CollectionUtils.isNotEmpty(null));
    }

    @Test
    @DisplayName("Test isNotEmpty with empty collection")
    void testIsNotEmpty_Empty() {
        assertFalse(CollectionUtils.isNotEmpty(new ArrayList<>()));
    }

    @Test
    @DisplayName("Test isNotEmpty with non-empty collection")
    void testIsNotEmpty_NonEmpty() {
        assertTrue(CollectionUtils.isNotEmpty(Arrays.asList("test")));
    }

    @Test
    @DisplayName("Test safeList with null list")
    void testSafeList_Null() {
        List<String> result = CollectionUtils.safeList(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Test safeList with non-null list")
    void testSafeList_NonNull() {
        List<String> input = Arrays.asList("test");
        List<String> result = CollectionUtils.safeList(input);
        assertSame(input, result);
    }

    @Test
    @DisplayName("Test firstOrNull with null list")
    void testFirstOrNull_Null() {
        assertNull(CollectionUtils.firstOrNull(null));
    }

    @Test
    @DisplayName("Test firstOrNull with empty list")
    void testFirstOrNull_Empty() {
        assertNull(CollectionUtils.firstOrNull(new ArrayList<>()));
    }

    @Test
    @DisplayName("Test firstOrNull with non-empty list")
    void testFirstOrNull_NonEmpty() {
        List<String> list = Arrays.asList("first", "second", "third");
        assertEquals("first", CollectionUtils.firstOrNull(list));
    }

    @Test
    @DisplayName("Test firstOrNull with single element list")
    void testFirstOrNull_SingleElement() {
        List<String> list = Collections.singletonList("only");
        assertEquals("only", CollectionUtils.firstOrNull(list));
    }

    @Test
    @DisplayName("Test constructor throws exception")
    void testConstructor() throws Exception {
        var constructor = CollectionUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(UnsupportedOperationException.class, () -> {
            try {
                constructor.newInstance();
            } catch (java.lang.reflect.InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }
}
