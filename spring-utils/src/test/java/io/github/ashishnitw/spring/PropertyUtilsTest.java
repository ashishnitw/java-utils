package io.github.ashishnitw.spring;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for PropertyUtils.
 */
class PropertyUtilsTest {

    private static final String TEST_PROPERTY = "test.property";
    private static final String TEST_VALUE = "test_value";

    @AfterEach
    void cleanup() {
        // Clean up any test properties set
        System.clearProperty(TEST_PROPERTY);
    }

    @Test
    void testGetSystemProperty() {
        System.setProperty(TEST_PROPERTY, TEST_VALUE);
        assertEquals(TEST_VALUE, PropertyUtils.getSystemProperty(TEST_PROPERTY));
    }

    @Test
    void testGetSystemPropertyNotFound() {
        assertNull(PropertyUtils.getSystemProperty("nonexistent.property"));
    }

    @Test
    void testGetEnvironmentVariable() {
        String path = PropertyUtils.getEnvironmentVariable("PATH");
        assertNotNull(path);
        assertFalse(path.isEmpty());
    }

    @Test
    void testGetEnvironmentVariableNotFound() {
        String result = PropertyUtils.getEnvironmentVariable("nonexistent_env_var_12345");
        assertNull(result);
    }

    @Test
    void testGetSystemPropertyOrDefault_WithValue() {
        System.setProperty(TEST_PROPERTY, TEST_VALUE);
        String result = PropertyUtils.getSystemPropertyOrDefault(TEST_PROPERTY, "default");
        assertEquals(TEST_VALUE, result);
    }

    @Test
    void testGetSystemPropertyOrDefault_WithDefault() {
        String result = PropertyUtils.getSystemPropertyOrDefault("nonexistent.property", "default");
        assertEquals("default", result);
    }

    @Test
    void testGetEnvironmentVariableOrDefault_WithValue() {
        String path = PropertyUtils.getEnvironmentVariableOrDefault("PATH", "default");
        assertNotNull(path);
        assertNotEquals("default", path);
    }

    @Test
    void testGetEnvironmentVariableOrDefault_WithDefault() {
        String result = PropertyUtils.getEnvironmentVariableOrDefault("nonexistent_env_var_12345", "default");
        assertEquals("default", result);
    }

    @Test
    void testGetAllSystemProperties() {
        System.setProperty(TEST_PROPERTY, TEST_VALUE);
        Map<String, String> properties = PropertyUtils.getAllSystemProperties();
        
        assertNotNull(properties);
        assertFalse(properties.isEmpty());
        assertEquals(TEST_VALUE, properties.get(TEST_PROPERTY));
    }

    @Test
    void testGetAllEnvironmentVariables() {
        Map<String, String> variables = PropertyUtils.getAllEnvironmentVariables();
        
        assertNotNull(variables);
        assertFalse(variables.isEmpty());
        // Most systems have PATH variable
        assertTrue(variables.containsKey("PATH") || variables.containsKey("Path"));
    }

    @Test
    void testHasSystemProperty() {
        System.setProperty(TEST_PROPERTY, TEST_VALUE);
        assertTrue(PropertyUtils.hasSystemProperty(TEST_PROPERTY));
        assertFalse(PropertyUtils.hasSystemProperty("nonexistent.property"));
    }

    @Test
    void testHasEnvironmentVariable() {
        assertTrue(PropertyUtils.hasEnvironmentVariable("PATH") || 
                   PropertyUtils.hasEnvironmentVariable("Path"));
        assertFalse(PropertyUtils.hasEnvironmentVariable("nonexistent_env_var_12345"));
    }

    @Test
    void testSetSystemProperty() {
        System.clearProperty(TEST_PROPERTY);
        String previous = PropertyUtils.setSystemProperty(TEST_PROPERTY, TEST_VALUE);
        
        assertNull(previous);
        assertEquals(TEST_VALUE, PropertyUtils.getSystemProperty(TEST_PROPERTY));
    }

    @Test
    void testSetSystemProperty_ReplaceValue() {
        String oldValue = "old_value";
        PropertyUtils.setSystemProperty(TEST_PROPERTY, oldValue);
        
        String previous = PropertyUtils.setSystemProperty(TEST_PROPERTY, TEST_VALUE);
        
        assertEquals(oldValue, previous);
        assertEquals(TEST_VALUE, PropertyUtils.getSystemProperty(TEST_PROPERTY));
    }

    @Test
    void testUtilityClassCannotBeInstantiated() {
        try {
            var constructor = PropertyUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
            assertTrue(exception.getCause() instanceof UnsupportedOperationException);
        } catch (NoSuchMethodException e) {
            fail("Constructor not found");
        }
    }
}
