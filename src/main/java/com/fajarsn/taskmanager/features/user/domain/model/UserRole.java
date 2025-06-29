package com.fajarsn.taskmanager.features.user.domain.model;

/**
 * Enum representing user roles in the system.
 * Defines the access level and permissions for users.
 */
public enum UserRole {
    USER("User", "Standard user with basic permissions"),
    MANAGER("Manager", "Manager with team oversight permissions"),
    ADMIN("Admin", "Administrator with full system access");

    private final String displayName;
    private final String description;

    UserRole(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasHigherPrivilegesThan(UserRole other) {
        return this.ordinal() > other.ordinal();
    }
}
