package io.github.ashishnitw.spring;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for BeanUtils.
 */
class BeanUtilsTest {

    @Test
    void testIsNull() {
        assertTrue(BeanUtils.isNull(null));
        assertFalse(BeanUtils.isNull(""));
        assertFalse(BeanUtils.isNull("test"));
    }

    @Test
    void testIsNotNull() {
        assertFalse(BeanUtils.isNotNull(null));
        assertTrue(BeanUtils.isNotNull(""));
        assertTrue(BeanUtils.isNotNull("test"));
    }

    @Test
    void testGetSimpleClassName() {
        assertEquals("null", BeanUtils.getSimpleClassName(null));
        assertEquals("String", BeanUtils.getSimpleClassName("test"));
        assertEquals("Integer", BeanUtils.getSimpleClassName(123));
    }

    @Test
    void testGetFullyQualifiedClassName() {
        assertEquals("null", BeanUtils.getFullyQualifiedClassName(null));
        assertEquals("java.lang.String", BeanUtils.getFullyQualifiedClassName("test"));
        assertEquals("java.lang.Integer", BeanUtils.getFullyQualifiedClassName(123));
    }

    @Test
    void testSafeCast_Success() {
        String value = "test";
        String result = BeanUtils.safeCast(value, String.class);
        assertEquals("test", result);
    }

    @Test
    void testSafeCast_Null() {
        String result = BeanUtils.safeCast(null, String.class);
        assertNull(result);
    }

    @Test
    void testSafeCast_Failure() {
        Integer value = 123;
        String result = BeanUtils.safeCast(value, String.class);
        assertNull(result);
    }

    @Test
    void testIsInstanceOf() {
        assertTrue(BeanUtils.isInstanceOf("test", String.class));
        assertFalse(BeanUtils.isInstanceOf(123, String.class));
        assertFalse(BeanUtils.isInstanceOf(null, String.class));
    }

    @Test
    void testGetOrDefault_WithValue() {
        String result = BeanUtils.getOrDefault("test", "default");
        assertEquals("test", result);
    }

    @Test
    void testGetOrDefault_WithNull() {
        String result = BeanUtils.getOrDefault(null, "default");
        assertEquals("default", result);
    }

    @Test
    void testUtilityClassCannotBeInstantiated() {
        try {
            var constructor = BeanUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
            assertTrue(exception.getCause() instanceof UnsupportedOperationException);
        } catch (NoSuchMethodException e) {
            fail("Constructor not found");
        }
    }
}
