package com.fajarsn.taskmanager.features.user.application.responses;

import com.fajarsn.taskmanager.features.user.domain.model.UserRole;
import com.fajarsn.taskmanager.features.user.domain.model.UserStatus;
import com.fajarsn.taskmanager.shared.application.Response;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response DTO for user operations.
 */
public record UserResponse(
        UUID id,
        String email,
        String firstName,
        String lastName,
        String fullName,
        UserRole role,
        UserStatus status,
        String profilePictureUrl,
        LocalDateTime lastLoginAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean isActive,
        boolean canManageUsers,

        // Response metadata
        UUID responseId,
        LocalDateTime responseTimestamp,
        String message
) implements Response {
    /**
     * Primary constructor for successful operations
     */
    public UserResponse(
            UUID id,
            String email,
            String firstName,
            String lastName,
            String fullName,
            UserRole role,
            UserStatus status,
            String profilePictureUrl,
            LocalDateTime lastLoginAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            boolean isActive,
            boolean canManageUsers
    ) {
        this(
                id,
                email,
                firstName,
                lastName,
                fullName,
                role,
                status,
                profilePictureUrl,
                lastLoginAt,
                createdAt,
                updatedAt,
                isActive,
                canManageUsers,
                UUID.randomUUID(),
                LocalDateTime.now(),
                null
        );
    }

    /**
     * Constructor with custom message
     */
    public UserResponse(
            UUID id,
            String email,
            String firstName,
            String lastName,
            String fullName,
            UserRole role,
            UserStatus status,
            String profilePictureUrl,
            LocalDateTime lastLoginAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            boolean isActive,
            boolean canManageUsers,
            String message
    ) {
        this(
                id,
                email,
                firstName,
                lastName,
                fullName,
                role,
                status,
                profilePictureUrl,
                lastLoginAt,
                createdAt,
                updatedAt,
                isActive,
                canManageUsers,
                UUID.randomUUID(),
                LocalDateTime.now(),
                message
        );
    }

    @Override
    public UUID getResponseId() {
        return responseId;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return responseTimestamp;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
