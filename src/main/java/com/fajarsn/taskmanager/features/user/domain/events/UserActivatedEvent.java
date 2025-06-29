package com.fajarsn.taskmanager.features.user.domain.events;

import com.fajarsn.taskmanager.shared.domain.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain event fired when a user account is activated.
 */
public record UserActivatedEvent(UUID eventId, LocalDateTime occurredOn, UUID userId) implements DomainEvent {
    public UserActivatedEvent(UUID userId) {
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
        return "UserActivated";
    }
}
