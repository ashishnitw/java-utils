package io.github.ashishnitw.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ValidationUtils.
 */
class ValidationUtilsTest {

    @Test
    @DisplayName("Test isValidEmail with valid email")
    void testIsValidEmail_Valid() {
        assertTrue(ValidationUtils.isValidEmail("test@example.com"));
        assertTrue(ValidationUtils.isValidEmail("user.name@example.co.uk"));
        assertTrue(ValidationUtils.isValidEmail("user+tag@example.com"));
    }

    @Test
    @DisplayName("Test isValidEmail with invalid email")
    void testIsValidEmail_Invalid() {
        assertFalse(ValidationUtils.isValidEmail("invalid"));
        assertFalse(ValidationUtils.isValidEmail("@example.com"));
        assertFalse(ValidationUtils.isValidEmail("user@"));
        assertFalse(ValidationUtils.isValidEmail("user @example.com"));
    }

    @Test
    @DisplayName("Test isValidEmail with null or empty")
    void testIsValidEmail_NullOrEmpty() {
        assertFalse(ValidationUtils.isValidEmail(null));
        assertFalse(ValidationUtils.isValidEmail(""));
    }

    @Test
    @DisplayName("Test isValidUrl with valid URL")
    void testIsValidUrl_Valid() {
        assertTrue(ValidationUtils.isValidUrl("http://example.com"));
        assertTrue(ValidationUtils.isValidUrl("https://www.example.com"));
        assertTrue(ValidationUtils.isValidUrl("https://example.com/path"));
    }

    @Test
    @DisplayName("Test isValidUrl with invalid URL")
    void testIsValidUrl_Invalid() {
        assertFalse(ValidationUtils.isValidUrl("invalid"));
        assertFalse(ValidationUtils.isValidUrl("htp://example.com"));
    }

    @Test
    @DisplayName("Test isValidUrl with null or empty")
    void testIsValidUrl_NullOrEmpty() {
        assertFalse(ValidationUtils.isValidUrl(null));
        assertFalse(ValidationUtils.isValidUrl(""));
    }

    @Test
    @DisplayName("Test isNumeric with numeric string")
    void testIsNumeric_Valid() {
        assertTrue(ValidationUtils.isNumeric("123"));
        assertTrue(ValidationUtils.isNumeric("0"));
        assertTrue(ValidationUtils.isNumeric("999999"));
    }

    @Test
    @DisplayName("Test isNumeric with non-numeric string")
    void testIsNumeric_Invalid() {
        assertFalse(ValidationUtils.isNumeric("abc"));
        assertFalse(ValidationUtils.isNumeric("12.34"));
        assertFalse(ValidationUtils.isNumeric("12a34"));
        assertFalse(ValidationUtils.isNumeric("-123"));
    }

    @Test
    @DisplayName("Test isNumeric with null or empty")
    void testIsNumeric_NullOrEmpty() {
        assertFalse(ValidationUtils.isNumeric(null));
        assertFalse(ValidationUtils.isNumeric(""));
    }

    @Test
    @DisplayName("Test isAlphanumeric with alphanumeric string")
    void testIsAlphanumeric_Valid() {
        assertTrue(ValidationUtils.isAlphanumeric("abc123"));
        assertTrue(ValidationUtils.isAlphanumeric("ABC"));
        assertTrue(ValidationUtils.isAlphanumeric("123"));
    }

    @Test
    @DisplayName("Test isAlphanumeric with non-alphanumeric string")
    void testIsAlphanumeric_Invalid() {
        assertFalse(ValidationUtils.isAlphanumeric("abc 123"));
        assertFalse(ValidationUtils.isAlphanumeric("abc-123"));
        assertFalse(ValidationUtils.isAlphanumeric("abc@123"));
    }

    @Test
    @DisplayName("Test isAlphanumeric with null or empty")
    void testIsAlphanumeric_NullOrEmpty() {
        assertFalse(ValidationUtils.isAlphanumeric(null));
        assertFalse(ValidationUtils.isAlphanumeric(""));
    }

    @Test
    @DisplayName("Test isNotEmpty with non-empty string")
    void testIsNotEmpty_Valid() {
        assertTrue(ValidationUtils.isNotEmpty("test"));
        assertTrue(ValidationUtils.isNotEmpty(" "));
    }

    @Test
    @DisplayName("Test isNotEmpty with null or empty")
    void testIsNotEmpty_Invalid() {
        assertFalse(ValidationUtils.isNotEmpty(null));
        assertFalse(ValidationUtils.isNotEmpty(""));
    }

    @Test
    @DisplayName("Test constructor throws exception")
    void testConstructor() throws Exception {
        var constructor = ValidationUtils.class.getDeclaredConstructor();
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
