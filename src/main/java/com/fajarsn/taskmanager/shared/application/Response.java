package com.fajarsn.taskmanager.shared.application;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base interface for all response objects.
 * Provides default implementations for common functionality.
 */
public interface Response {
    /**
     * Get unique response ID.
     * Default implementation generates a random UUID.
     */
    default UUID getResponseId() {
        return UUID.randomUUID();
    }

    /**
     * Get timestamp when this response was created.
     * Default implementation returns current time.
     */
    default LocalDateTime getTimestamp() {
        return LocalDateTime.now();
    }

    /**
     * Check if the operation was successful.
     * Default implementation assumes success.
     */
    default boolean isSuccess() {
        return true;
    }

    /**
     * Get optional message.
     * Default implementation returns null.
     */
    default String getMessage() {
        return null;
    }
}
