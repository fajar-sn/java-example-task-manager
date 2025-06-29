package com.fajarsn.taskmanager.shared.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class JsonService {
    private static final Logger logger = LoggerFactory.getLogger(JsonService.class);

    private final ObjectMapper objectMapper;

    public JsonService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * Serialize object to JSON string
     */
    public <T> String toJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize object to JSON", e);
            throw new RuntimeException("JSON serialization failed", e);
        }
    }

    /**
     * Deserialize JSON string to object
     */
    public <T> T fromJson(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            logger.error("Failed to deserialize JSON to object", e);
            throw new RuntimeException("JSON deserialization failed", e);
        }
    }

    /**
     * Serialize object to JSON and write to OutputStream
     */
    public <T> void writeJson(T object, OutputStream outputStream) {
        try {
            objectMapper.writeValue(outputStream, object);
        } catch (IOException e) {
            logger.error("Failed to write JSON to stream", e);
            throw new RuntimeException("JSON writing failed", e);
        }
    }

    /**
     * Read JSON from InputStream and deserialize
     */
    public <T> T readJson(InputStream inputStream, Class<T> tClass) {
        try {
            return objectMapper.readValue(inputStream, tClass);
        } catch (IOException e) {
            logger.error("Failed to read JSON from stream", e);
            throw new RuntimeException("JSON reading failed", e);
        }
    }

    /**
     * Serialize list to JSON array
     */
    public <T> String toJsonArray(List<T> list) {
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize list to JSON array", e);
            throw new RuntimeException("JSON array serialization failed", e);
        }
    }

    /**
     * Deserialize JSON array to list
     */
    public <T> List<T> fromJsonArray(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, tClass));
        } catch (JsonProcessingException e) {
            logger.error("Failed to deserialize JSON array to list", e);
            throw new RuntimeException("JSON array deserialization failed", e);
        }
    }

    /**
     * Pretty print JSON
     */
    public String prettyPrint(String json) {
        try {
            Object jsonObject = objectMapper.readValue(json, Object.class);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            logger.error("Failed to pretty print JSON", e);
            return json; // Return original if formatting fails
        }
    }
}
