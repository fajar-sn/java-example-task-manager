package com.fajarsn.taskmanager.features.user.domain.events;

import com.fajarsn.taskmanager.shared.domain.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain event fired when a user logs into the system.
 */
public record UserLoggedInEvent(UUID eventId, LocalDateTime occurredOn, UUID userId,
                                LocalDateTime loginTime) implements DomainEvent {
    public UserLoggedInEvent(UUID userId, LocalDateTime loginTime) {
        this(UUID.randomUUID(), LocalDateTime.now(), userId, loginTime);
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
        return "UserLoggedIn";
    }
}
