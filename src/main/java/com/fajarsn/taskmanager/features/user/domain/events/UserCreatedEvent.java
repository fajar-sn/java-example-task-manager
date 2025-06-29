package com.fajarsn.taskmanager.features.user.domain.events;

import com.fajarsn.taskmanager.features.user.domain.model.UserRole;
import com.fajarsn.taskmanager.shared.domain.DomainEvent;
import com.fajarsn.taskmanager.shared.domain.Email;
import com.fajarsn.taskmanager.shared.domain.Name;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain event fired when a new user is created.
 */
public record UserCreatedEvent(UUID eventId, LocalDateTime occurredOn, UUID userId, Email email, Name name,
                               UserRole role) implements DomainEvent {
    public UserCreatedEvent(UUID userId, Email email, Name name, UserRole role) {
        this(UUID.randomUUID(), LocalDateTime.now(), userId, email, name, role);
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
        return "UserCreated";
    }
}
