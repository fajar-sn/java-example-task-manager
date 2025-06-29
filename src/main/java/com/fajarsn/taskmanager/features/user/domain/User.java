package com.fajarsn.taskmanager.features.user.domain;

import com.fajarsn.taskmanager.features.user.domain.events.*;
import com.fajarsn.taskmanager.features.user.domain.model.UserRole;
import com.fajarsn.taskmanager.features.user.domain.model.UserStatus;
import com.fajarsn.taskmanager.shared.domain.AggregateRoot;
import com.fajarsn.taskmanager.shared.domain.Email;
import com.fajarsn.taskmanager.shared.domain.Name;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User aggregate root representing a system user.
 * Manages user identity, profile information, and authentication state.
 */
public class User extends AggregateRoot {
    private Email email;
    private Name name;
    private String passwordHash;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime lastLoginAt;
    private String profilePictureUrl;

    // For JPA
    protected User() {
        super();
    }

    public User(Email email, Name name, String passwordHash, UserRole role) {
        super();
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.passwordHash = Objects.requireNonNull(passwordHash, "Password cannot be null");
        this.role = Objects.requireNonNull(role, "User role cannot be null");
        this.status = UserStatus.ACTIVE;

        // Publish domain event
        addDomainEvent(new UserCreatedEvent(getId(), email, name, role));
    }

    public void updateProfile(Name name, String profilePictureUrl) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.profilePictureUrl = profilePictureUrl;
        updateTimestamp();

        addDomainEvent(new UserProfileUpdatedEvent(getId(), name, profilePictureUrl));
    }

    public void changePassword(String newPasswordHash) {
        if (newPasswordHash == null || newPasswordHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Password hash cannot be null or empty");
        }

        this.passwordHash = passwordHash;
        updateTimestamp();

        addDomainEvent(new UserPasswordChangedEvent(getId()));
    }

    public void changeRole(UserRole newRole) {
        if (this.role == newRole) {
            return; // No change needed
        }

        UserRole oldRole = this.role;
        this.role = Objects.requireNonNull(newRole, "User role cannot be null");
        updateTimestamp();

        addDomainEvent(new UserRoleChangedEvent(getId(), oldRole, newRole));
    }

    public void deactivate() {
        if (this.status == UserStatus.INACTIVE) {
            return; // Already inactive
        }

        this.status = UserStatus.INACTIVE;
        updateTimestamp();

        addDomainEvent(new UserDeactivatedEvent(getId()));
    }

    public void activate() {
        if (this.status == UserStatus.ACTIVE) {
            return; // Already active
        }

        this.status = UserStatus.ACTIVE;
        updateTimestamp();

        addDomainEvent(new UserActivatedEvent(getId()));
    }

    public void recordLogin() {
        this.lastLoginAt = LocalDateTime.now();
        updateTimestamp();

        addDomainEvent(new UserLoggedInEvent(getId(), lastLoginAt));
    }

    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }

    public boolean hasRole(UserRole role) {
        return this.role == role;
    }

    public boolean canManageUsers() {
        return role == UserRole.ADMIN || role == UserRole.MANAGER;
    }

    // Getters
    public Email getEmail() {
        return email;
    }

    public Name getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
}
