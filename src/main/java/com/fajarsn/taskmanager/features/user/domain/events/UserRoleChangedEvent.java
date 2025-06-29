package com.fajarsn.taskmanager.features.user.domain.events;

import com.fajarsn.taskmanager.features.user.domain.model.UserRole;
import com.fajarsn.taskmanager.shared.domain.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain event fired when a user's role is changed.
 */
public record UserRoleChangedEvent(UUID eventId, LocalDateTime occurredOn, UUID userID, UserRole oldRole,
                                   UserRole newRole) implements DomainEvent {
    public UserRoleChangedEvent(UUID userID, UserRole oldRole, UserRole newRole) {
        this(UUID.randomUUID(), LocalDateTime.now(), userID, oldRole, newRole);
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public LocalDateTime getOccuredOn() {
        return occurredOn;
    }

    @Override
    public String getEventType() {
        return "UserRoleChanged";
    }
}
