package io.github.ashishnitw.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for StringUtils.
 */
class StringUtilsTest {

    @Test
    @DisplayName("Test isEmpty with null string")
    void testIsEmpty_Null() {
        assertTrue(StringUtils.isEmpty(null));
    }

    @Test
    @DisplayName("Test isEmpty with empty string")
    void testIsEmpty_Empty() {
        assertTrue(StringUtils.isEmpty(""));
    }

    @Test
    @DisplayName("Test isEmpty with non-empty string")
    void testIsEmpty_NonEmpty() {
        assertFalse(StringUtils.isEmpty("test"));
    }

    @Test
    @DisplayName("Test isBlank with null string")
    void testIsBlank_Null() {
        assertTrue(StringUtils.isBlank(null));
    }

    @Test
    @DisplayName("Test isBlank with empty string")
    void testIsBlank_Empty() {
        assertTrue(StringUtils.isBlank(""));
    }

    @Test
    @DisplayName("Test isBlank with whitespace string")
    void testIsBlank_Whitespace() {
        assertTrue(StringUtils.isBlank("   "));
    }

    @Test
    @DisplayName("Test isBlank with non-blank string")
    void testIsBlank_NonBlank() {
        assertFalse(StringUtils.isBlank("test"));
    }

    @Test
    @DisplayName("Test capitalize with null string")
    void testCapitalize_Null() {
        assertNull(StringUtils.capitalize(null));
    }

    @Test
    @DisplayName("Test capitalize with empty string")
    void testCapitalize_Empty() {
        assertEquals("", StringUtils.capitalize(""));
    }

    @Test
    @DisplayName("Test capitalize with lowercase string")
    void testCapitalize_Lowercase() {
        assertEquals("Test", StringUtils.capitalize("test"));
    }

    @Test
    @DisplayName("Test capitalize with already capitalized string")
    void testCapitalize_AlreadyCapitalized() {
        assertEquals("Test", StringUtils.capitalize("Test"));
    }

    @Test
    @DisplayName("Test truncate with null string")
    void testTruncate_Null() {
        assertNull(StringUtils.truncate(null, 5));
    }

    @Test
    @DisplayName("Test truncate with string shorter than max length")
    void testTruncate_Shorter() {
        assertEquals("test", StringUtils.truncate("test", 10));
    }

    @Test
    @DisplayName("Test truncate with string longer than max length")
    void testTruncate_Longer() {
        assertEquals("test", StringUtils.truncate("testing", 4));
    }

    @Test
    @DisplayName("Test truncate with negative max length")
    void testTruncate_NegativeLength() {
        assertThrows(IllegalArgumentException.class, () -> StringUtils.truncate("test", -1));
    }

    @Test
    @DisplayName("Test nullToEmpty with null string")
    void testNullToEmpty_Null() {
        assertEquals("", StringUtils.nullToEmpty(null));
    }

    @Test
    @DisplayName("Test nullToEmpty with non-null string")
    void testNullToEmpty_NonNull() {
        assertEquals("test", StringUtils.nullToEmpty("test"));
    }

    @Test
    @DisplayName("Test emptyToNull with empty string")
    void testEmptyToNull_Empty() {
        assertNull(StringUtils.emptyToNull(""));
    }

    @Test
    @DisplayName("Test emptyToNull with null string")
    void testEmptyToNull_Null() {
        assertNull(StringUtils.emptyToNull(null));
    }

    @Test
    @DisplayName("Test emptyToNull with non-empty string")
    void testEmptyToNull_NonEmpty() {
        assertEquals("test", StringUtils.emptyToNull("test"));
    }

    @Test
    @DisplayName("Test constructor throws exception")
    void testConstructor() throws Exception {
        var constructor = StringUtils.class.getDeclaredConstructor();
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
