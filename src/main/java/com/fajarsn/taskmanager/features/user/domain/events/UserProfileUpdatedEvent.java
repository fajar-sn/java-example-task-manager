package com.fajarsn.taskmanager.features.user.domain.events;

import com.fajarsn.taskmanager.shared.domain.DomainEvent;
import com.fajarsn.taskmanager.shared.domain.Name;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain event fired when a user's profile is updated
 */
public record UserProfileUpdatedEvent(UUID eventId, LocalDateTime occurredOn, UUID userId, Name newName,
                                      String profilePictureUrl) implements DomainEvent {
    public UserProfileUpdatedEvent(UUID userId, Name newName, String profilePictureUrl) {
        this(UUID.randomUUID(), LocalDateTime.now(), userId, newName, profilePictureUrl);
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
        return "UserProfileUpdated";
    }
}
