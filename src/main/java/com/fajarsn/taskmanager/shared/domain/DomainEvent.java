package com.fajarsn.taskmanager.shared.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base interface for all domain events.
 * Domain events represent something important that happened in the domain.
 */
public interface DomainEvent {
    UUID getEventId();
    LocalDateTime getOccuredOn();
    String getEventType();
}
