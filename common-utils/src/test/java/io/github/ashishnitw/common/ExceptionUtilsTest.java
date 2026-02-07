package io.github.ashishnitw.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ExceptionUtils.
 */
class ExceptionUtilsTest {

    @Test
    @DisplayName("Test getStackTraceAsString with valid exception")
    void testGetStackTraceAsString_Valid() {
        Exception ex = new RuntimeException("Test exception");
        String stackTrace = ExceptionUtils.getStackTraceAsString(ex);
        assertNotNull(stackTrace);
        assertTrue(stackTrace.contains("Test exception"));
        assertTrue(stackTrace.contains("RuntimeException"));
    }

    @Test
    @DisplayName("Test getStackTraceAsString with null throws exception")
    void testGetStackTraceAsString_Null() {
        assertThrows(IllegalArgumentException.class, () -> 
            ExceptionUtils.getStackTraceAsString(null));
    }

    @Test
    @DisplayName("Test getRootCause with single exception")
    void testGetRootCause_SingleException() {
        Exception ex = new RuntimeException("Root cause");
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        assertSame(ex, rootCause);
    }

    @Test
    @DisplayName("Test getRootCause with chained exceptions")
    void testGetRootCause_ChainedExceptions() {
        Exception rootEx = new IllegalArgumentException("Root cause");
        Exception midEx = new IllegalStateException("Middle exception", rootEx);
        Exception topEx = new RuntimeException("Top exception", midEx);
        
        Throwable rootCause = ExceptionUtils.getRootCause(topEx);
        assertSame(rootEx, rootCause);
    }

    @Test
    @DisplayName("Test getRootCause with null throws exception")
    void testGetRootCause_Null() {
        assertThrows(IllegalArgumentException.class, () -> 
            ExceptionUtils.getRootCause(null));
    }

    @Test
    @DisplayName("Test getRootCauseMessage with single exception")
    void testGetRootCauseMessage_SingleException() {
        Exception ex = new RuntimeException("Root message");
        String message = ExceptionUtils.getRootCauseMessage(ex);
        assertEquals("Root message", message);
    }

    @Test
    @DisplayName("Test getRootCauseMessage with chained exceptions")
    void testGetRootCauseMessage_ChainedExceptions() {
        Exception rootEx = new IllegalArgumentException("Root message");
        Exception midEx = new IllegalStateException("Middle message", rootEx);
        Exception topEx = new RuntimeException("Top message", midEx);
        
        String message = ExceptionUtils.getRootCauseMessage(topEx);
        assertEquals("Root message", message);
    }

    @Test
    @DisplayName("Test getRootCauseMessage with null throws exception")
    void testGetRootCauseMessage_Null() {
        assertThrows(IllegalArgumentException.class, () -> 
            ExceptionUtils.getRootCauseMessage(null));
    }

    @Test
    @DisplayName("Test getRootCauseMessage with exception having no message")
    void testGetRootCauseMessage_NoMessage() {
        Exception ex = new RuntimeException();
        String message = ExceptionUtils.getRootCauseMessage(ex);
        assertNull(message);
    }

    @Test
    @DisplayName("Test constructor throws exception")
    void testConstructor() throws Exception {
        var constructor = ExceptionUtils.class.getDeclaredConstructor();
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
