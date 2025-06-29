package com.fajarsn.taskmanager.features.user.application.commands;

import com.fajarsn.taskmanager.shared.application.Command;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Command for user authentication.
 */
public record AuthenticateUserCommand(
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Password is required")
        String password,

        // Command metadata
        UUID requestId,
        LocalDateTime timestamp
) implements Command {
    public AuthenticateUserCommand(String email, String password) {
        this(email, password, UUID.randomUUID(), LocalDateTime.now());
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
    public boolean isValid() {
        return email != null && !email.trim().isEmpty() &&
                password != null && !password.trim().isEmpty();
    }
}
