package com.ashishnitw.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for DateTimeUtils.
 */
class DateTimeUtilsTest {

    @Test
    @DisplayName("Test getCurrentTimestamp returns non-null value")
    void testGetCurrentTimestamp() {
        LocalDateTime timestamp = DateTimeUtils.getCurrentTimestamp();
        assertNotNull(timestamp);
    }

    @Test
    @DisplayName("Test formatDate with valid date and pattern")
    void testFormatDate_Valid() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
        String formatted = DateTimeUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
        assertEquals("2024-01-15 10:30:00", formatted);
    }

    @Test
    @DisplayName("Test formatDate with null date throws exception")
    void testFormatDate_NullDate() {
        assertThrows(IllegalArgumentException.class, () -> 
            DateTimeUtils.formatDate(null, "yyyy-MM-dd"));
    }

    @Test
    @DisplayName("Test formatDate with null pattern throws exception")
    void testFormatDate_NullPattern() {
        LocalDateTime date = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class, () -> 
            DateTimeUtils.formatDate(date, null));
    }

    @Test
    @DisplayName("Test parseDate with valid string and pattern")
    void testParseDate_Valid() {
        LocalDateTime parsed = DateTimeUtils.parseDate("2024-01-15 10:30:00", "yyyy-MM-dd HH:mm:ss");
        assertEquals(2024, parsed.getYear());
        assertEquals(1, parsed.getMonthValue());
        assertEquals(15, parsed.getDayOfMonth());
        assertEquals(10, parsed.getHour());
        assertEquals(30, parsed.getMinute());
    }

    @Test
    @DisplayName("Test parseDate with null string throws exception")
    void testParseDate_NullString() {
        assertThrows(IllegalArgumentException.class, () -> 
            DateTimeUtils.parseDate(null, "yyyy-MM-dd"));
    }

    @Test
    @DisplayName("Test parseDate with null pattern throws exception")
    void testParseDate_NullPattern() {
        assertThrows(IllegalArgumentException.class, () -> 
            DateTimeUtils.parseDate("2024-01-15", null));
    }

    @Test
    @DisplayName("Test parseDate with invalid string throws exception")
    void testParseDate_InvalidString() {
        assertThrows(DateTimeParseException.class, () -> 
            DateTimeUtils.parseDate("invalid", "yyyy-MM-dd"));
    }

    @Test
    @DisplayName("Test toEpochMilli with valid date")
    void testToEpochMilli_Valid() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
        long epochMilli = DateTimeUtils.toEpochMilli(date);
        assertTrue(epochMilli > 0);
    }

    @Test
    @DisplayName("Test toEpochMilli with null date throws exception")
    void testToEpochMilli_Null() {
        assertThrows(IllegalArgumentException.class, () -> 
            DateTimeUtils.toEpochMilli(null));
    }

    @Test
    @DisplayName("Test fromEpochMilli with valid epoch")
    void testFromEpochMilli_Valid() {
        long epochMilli = 1705318200000L; // Some timestamp
        LocalDateTime date = DateTimeUtils.fromEpochMilli(epochMilli);
        assertNotNull(date);
    }

    @Test
    @DisplayName("Test round-trip conversion with toEpochMilli and fromEpochMilli")
    void testRoundTripConversion() {
        LocalDateTime original = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
        long epochMilli = DateTimeUtils.toEpochMilli(original);
        LocalDateTime converted = DateTimeUtils.fromEpochMilli(epochMilli);
        assertEquals(original, converted);
    }

    @Test
    @DisplayName("Test constructor throws exception")
    void testConstructor() throws Exception {
        var constructor = DateTimeUtils.class.getDeclaredConstructor();
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
