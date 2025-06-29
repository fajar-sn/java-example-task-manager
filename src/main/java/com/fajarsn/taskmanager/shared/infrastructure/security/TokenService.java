package com.fajarsn.taskmanager.shared.infrastructure.security;

import com.fajarsn.taskmanager.features.user.domain.User;

import java.util.UUID;

/**
 * Service for JWT token operations.
 */
public interface TokenService {
    String generateToken(User user);
    long getTokenExpirationTime();
    boolean validateToken(String token);
    UUID getUserIdFromToken(String token);
}
