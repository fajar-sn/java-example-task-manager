package com.fajarsn.taskmanager.features.user.domain.events;

import com.fajarsn.taskmanager.shared.domain.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain event fired when a user changes their password.
 */
public record UserPasswordChangedEvent(UUID eventId, LocalDateTime occurredOn, UUID userId) implements DomainEvent {
    public UserPasswordChangedEvent(UUID userId) {
        this(UUID.randomUUID(), LocalDateTime.now(), userId);
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
        return "UserPasswordChanged";
    }
}
