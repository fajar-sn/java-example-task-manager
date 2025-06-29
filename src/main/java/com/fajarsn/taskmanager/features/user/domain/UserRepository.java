package com.fajarsn.taskmanager.features.user.domain;

import com.fajarsn.taskmanager.features.user.domain.model.UserRole;
import com.fajarsn.taskmanager.features.user.domain.model.UserStatus;
import com.fajarsn.taskmanager.shared.domain.Email;
import com.fajarsn.taskmanager.shared.infrastructure.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for User aggregate.
 * Defines data access operations for users.
 */
public interface UserRepository extends Repository<User, UUID> {
    /**
     * Find a user by their email address
     */
    Optional<User> findByEmail(Email email);

    /**
     * Find all users with a specific role
     */
    List<User> findByRole(UserRole role);

    /**
     * Find all users with a specific status
     */
    List<User> findByStatus(UserStatus status);

    /**
     * Find all active users
     */
    List<User> findActiveUsers();

    /**
     * Find users by name (partial match)
     */
    List<User> findByNameContaining(String nameFragment);

    /**
     * Get count of users by status
     */
    long countByStatus(UserStatus status);

    /**
     * Check if a user exists with the given email
     */
    boolean existByEmail(Email email);
}
