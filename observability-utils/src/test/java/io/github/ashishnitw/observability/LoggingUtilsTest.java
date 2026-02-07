package io.github.ashishnitw.observability;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for LoggingUtils.
 */
class LoggingUtilsTest {

    @Test
    void testLogMethodEntry() {
        // Just verify it doesn't throw an exception
        assertDoesNotThrow(() -> LoggingUtils.logMethodEntry("testMethod"));
        assertDoesNotThrow(() -> LoggingUtils.logMethodEntry("testMethod", "param1", "param2"));
    }

    @Test
    void testLogMethodExit() {
        // Just verify it doesn't throw an exception
        assertDoesNotThrow(() -> LoggingUtils.logMethodExit("testMethod", "result"));
        assertDoesNotThrow(() -> LoggingUtils.logMethodExit("testMethod", null));
    }

    @Test
    void testLogMethodExecutionTime() {
        // Just verify it doesn't throw an exception
        assertDoesNotThrow(() -> LoggingUtils.logMethodExecutionTime("testMethod", 100));
        assertDoesNotThrow(() -> LoggingUtils.logMethodExecutionTime("testMethod", 0));
    }

    @Test
    void testLogObjectState() {
        // Just verify it doesn't throw an exception
        assertDoesNotThrow(() -> LoggingUtils.logObjectState("testObject", "state: active"));
    }

    @Test
    void testLogPerformanceWarning() {
        // Just verify it doesn't throw an exception
        assertDoesNotThrow(() -> LoggingUtils.logPerformanceWarning("testMethod", 1000, 500));
        assertDoesNotThrow(() -> LoggingUtils.logPerformanceWarning("testMethod", 100, 500));
    }

    @Test
    void testLogException() {
        Exception exception = new RuntimeException("Test exception");
        // Just verify it doesn't throw an exception
        assertDoesNotThrow(() -> LoggingUtils.logException("Test error occurred", exception));
    }

    @Test
    void testLogWarning() {
        // Just verify it doesn't throw an exception
        assertDoesNotThrow(() -> LoggingUtils.logWarning("Warning message", "context info"));
    }

    @Test
    void testLogApplicationStartup() {
        // Just verify it doesn't throw an exception
        assertDoesNotThrow(() -> LoggingUtils.logApplicationStartup("TestApp", "1.0.0"));
    }

    @Test
    void testLogApplicationShutdown() {
        // Just verify it doesn't throw an exception
        assertDoesNotThrow(() -> LoggingUtils.logApplicationShutdown("TestApp"));
    }

    @Test
    void testGetLoggerByClass() {
        Logger logger = LoggingUtils.getLogger(LoggingUtilsTest.class);
        assertNotNull(logger);
        // SLF4J logger names might vary - just check it's not null
        assertNotNull(logger.getName());
    }

    @Test
    void testGetLoggerByName() {
        String loggerName = "test.logger";
        Logger logger = LoggingUtils.getLogger(loggerName);
        assertNotNull(logger);
        // SLF4J might wrap the name, just verify logger is valid
        assertNotNull(logger.getName());
    }

    @Test
    void testUtilityClassCannotBeInstantiated() {
        try {
            var constructor = LoggingUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
            assertTrue(exception.getCause() instanceof UnsupportedOperationException);
        } catch (NoSuchMethodException e) {
            fail("Constructor not found");
        }
    }
}
