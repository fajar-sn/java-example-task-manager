package com.fajarsn.taskmanager.shared.application;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base interface for all command objects.
 * Provides default implementations for common functionality.
 */
public interface Command {
    /**
     * Get unique request ID for this command.
     * Default implementation generates a random UUID.
     */
    default UUID getRequestId() {
        return UUID.randomUUID();
    }

    /**
     * Get timestamp when this command was created.
     * Default implementation returns current time.
     */
    default LocalDateTime getTimeStamp() {
        return LocalDateTime.now();
    }

    /**
     * Get the ID of the user executing this command.
     * Override this method if user context is needed.
     */
    default UUID getExecutingUserId() {
        return null;
    }

    /**
     * Validate this command.
     * Override for custom validation logic.
     */
    default boolean isValid() {
        return true;
    }

    /**
     * Get command type for logging/auditing.
     */
    default String getCommandType() {
        return this.getClass().getSimpleName();
    }
}
