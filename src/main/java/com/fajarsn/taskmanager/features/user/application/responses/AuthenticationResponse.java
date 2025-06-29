package com.fajarsn.taskmanager.features.user.application.responses;

import com.fajarsn.taskmanager.shared.application.Response;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response for authentication operations.
 */
public record AuthenticationResponse(
        UserResponse user,
        String token,
        long expiresIn,
        String tokenType,

        // Response metadata
        UUID responseId,
        LocalDateTime responseTimestamp,
        boolean success,
        String message
) implements Response {
    /**
     * Constructor for successful authentication
     */
    public AuthenticationResponse(UserResponse user, String token, long expiresIn) {
        this(user, token, expiresIn, "Bearer", UUID.randomUUID(), LocalDateTime.now(), true, "Authentication success");
    }

    /**
     * Constructor for failed authentication.
     */
    public static AuthenticationResponse failure(String message) {
        return new AuthenticationResponse(null, null, 0, null, UUID.randomUUID(), LocalDateTime.now(), false, message);
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
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
