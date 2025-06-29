package com.fajarsn.taskmanager.features.user.application.commands;

import com.fajarsn.taskmanager.features.user.domain.model.UserRole;
import com.fajarsn.taskmanager.shared.application.Command;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Command for creating a new user
 */
public record CreateUserCommand(
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,

        @NotBlank(message = "User role is required")
        UserRole role,

        // Metadata field
        UUID requestId,
        LocalDateTime timestamp,
        UUID executingUserId
) implements Command {
    /**
     * Primary constructor for external use
     */
    public CreateUserCommand(String email, String firstName, String lastName, String password, UserRole role) {
        this(email, firstName, lastName, password, role, UUID.randomUUID(), LocalDateTime.now(), null);
    }

    /**
     * Constructor with executing user context
     */
    public CreateUserCommand(String email, String firstName, String lastName, String password, UserRole role, UUID executingUserId) {
        this(email, firstName, lastName, password, role, UUID.randomUUID(), LocalDateTime.now(), executingUserId);
    }

    @Override
    public UUID getRequestId() {
        return requestId;
    }

    @Override
    public LocalDateTime getTimeStamp() {
        return timestamp;
    }

    @Override
    public UUID getExecutingUserId() {
        return executingUserId;
    }

    @Override
    public boolean isValid() {
        return email != null && !email.trim().isEmpty() &&
                firstName != null && !firstName.trim().isEmpty() &&
                lastName != null && !lastName.trim().isEmpty() &&
                password != null && password.length() >= 8 &&
                role != null;
    }
}
