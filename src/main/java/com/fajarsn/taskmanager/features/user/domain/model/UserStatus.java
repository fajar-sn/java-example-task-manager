package com.fajarsn.taskmanager.features.user.domain.model;

/**
 * Enum representing user account status
 */
public enum UserStatus {
    ACTIVE("Active", "User account is active and can access the system"),
    INACTIVE("Inactive", "User account is deactivated and cannot access the system"),
    SUSPENDED("Suspended", "User account is temporarily suspended"),
    PENDING_ACTIVATION("Pending Activation", "User account is waiting for activation");

    private final String displayName;
    private final String description;

    UserStatus(String displayName, String description) {
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
