package com.ashishnitw.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for JSON serialization and deserialization operations.
 * This class uses Jackson ObjectMapper for JSON processing.
 * 
 * @author ashishnitw
 * @version 1.0.0
 * @since 1.0.0
 */
public final class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Private constructor to prevent instantiation.
     */
    private JsonUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Serializes an object to JSON string.
     *
     * @param obj the object to serialize
     * @return the JSON string representation
     * @throws IllegalArgumentException if obj is null
     * @throws RuntimeException if serialization fails
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize object to JSON: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }

    /**
     * Deserializes a JSON string to an object of the specified class.
     *
     * @param <T> the type of the object
     * @param json the JSON string
     * @param clazz the class of the object
     * @return the deserialized object
     * @throws IllegalArgumentException if json or clazz is null
     * @throws RuntimeException if deserialization fails
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null) {
            throw new IllegalArgumentException("JSON string cannot be null");
        }
        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            logger.error("Failed to deserialize JSON to object: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to deserialize JSON to object", e);
        }
    }

    /**
     * Serializes an object to a pretty-printed JSON string.
     *
     * @param obj the object to serialize
     * @return the pretty-printed JSON string representation
     * @throws IllegalArgumentException if obj is null
     * @throws RuntimeException if serialization fails
     */
    public static String toJsonPretty(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize object to pretty JSON: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to serialize object to pretty JSON", e);
        }
    }
}
