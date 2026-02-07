package io.github.ashishnitw.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for JsonUtils.
 */
class JsonUtilsTest {

    static class TestObject {
        private String name;
        private int age;

        public TestObject() {}

        public TestObject(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
    }

    @Test
    @DisplayName("Test toJson with valid object")
    void testToJson_Valid() {
        TestObject obj = new TestObject("John", 30);
        String json = JsonUtils.toJson(obj);
        assertNotNull(json);
        assertTrue(json.contains("John"));
        assertTrue(json.contains("30"));
    }

    @Test
    @DisplayName("Test toJson with null object throws exception")
    void testToJson_Null() {
        assertThrows(IllegalArgumentException.class, () -> 
            JsonUtils.toJson(null));
    }

    @Test
    @DisplayName("Test fromJson with valid JSON")
    void testFromJson_Valid() {
        String json = "{\"name\":\"John\",\"age\":30}";
        TestObject obj = JsonUtils.fromJson(json, TestObject.class);
        assertNotNull(obj);
        assertEquals("John", obj.getName());
        assertEquals(30, obj.getAge());
    }

    @Test
    @DisplayName("Test fromJson with null JSON throws exception")
    void testFromJson_NullJson() {
        assertThrows(IllegalArgumentException.class, () -> 
            JsonUtils.fromJson(null, TestObject.class));
    }

    @Test
    @DisplayName("Test fromJson with null class throws exception")
    void testFromJson_NullClass() {
        assertThrows(IllegalArgumentException.class, () -> 
            JsonUtils.fromJson("{}", null));
    }

    @Test
    @DisplayName("Test fromJson with invalid JSON throws exception")
    void testFromJson_InvalidJson() {
        assertThrows(RuntimeException.class, () -> 
            JsonUtils.fromJson("invalid json", TestObject.class));
    }

    @Test
    @DisplayName("Test toJsonPretty with valid object")
    void testToJsonPretty_Valid() {
        TestObject obj = new TestObject("John", 30);
        String json = JsonUtils.toJsonPretty(obj);
        assertNotNull(json);
        assertTrue(json.contains("John"));
        assertTrue(json.contains("30"));
        assertTrue(json.contains("\n")); // Pretty print should have newlines
    }

    @Test
    @DisplayName("Test toJsonPretty with null object throws exception")
    void testToJsonPretty_Null() {
        assertThrows(IllegalArgumentException.class, () -> 
            JsonUtils.toJsonPretty(null));
    }

    @Test
    @DisplayName("Test round-trip serialization and deserialization")
    void testRoundTrip() {
        TestObject original = new TestObject("Alice", 25);
        String json = JsonUtils.toJson(original);
        TestObject deserialized = JsonUtils.fromJson(json, TestObject.class);
        assertEquals(original.getName(), deserialized.getName());
        assertEquals(original.getAge(), deserialized.getAge());
    }

    @Test
    @DisplayName("Test constructor throws exception")
    void testConstructor() throws Exception {
        var constructor = JsonUtils.class.getDeclaredConstructor();
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
