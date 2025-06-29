package com.fajarsn.taskmanager.features.user.application.mappers;

import com.fajarsn.taskmanager.features.user.application.responses.UserResponse;
import com.fajarsn.taskmanager.features.user.domain.User;

/**
 * Mapper for converting User domain objects to response DTOs.
 * Updated to work with the new response structure.
 */
public final class UserMapper {

    private UserMapper() {
        // Utility class
    }

    /**
     * Convert User to UserResponse without message
     */
    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail().getValue(),
                user.getName().getFirstName(),
                user.getName().getLastName(),
                user.getName().getFullName(),
                user.getRole(),
                user.getStatus(),
                user.getProfilePictureUrl(),
                user.getLastLoginAt(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.isActive(),
                user.canManageUsers()
        );
    }

    /**
     * Convert User to UserResponse with custom message
     */
    public static UserResponse toResponse(User user, String message) {
        return new UserResponse(
                user.getId(),
                user.getEmail().getValue(),
                user.getName().getFirstName(),
                user.getName().getLastName(),
                user.getName().getFullName(),
                user.getRole(),
                user.getStatus(),
                user.getProfilePictureUrl(),
                user.getLastLoginAt(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.isActive(),
                user.canManageUsers(),
                message
        );
    }
}
