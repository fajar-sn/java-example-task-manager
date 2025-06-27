package com.fajarsn.taskmanager.shared.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Base class for Aggregate Roots in DDD.
 * Manages domain events and ensures consistency boundaries.
 */
public abstract class AggregateRoot extends Entity {
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    protected AggregateRoot() {
        super();
    }

    protected AggregateRoot(UUID id) {
        super(id);
    }

    /**
     * Add a domain event to be published
     */
    protected void addDomainEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    /**
     * Get all domain events and clear the list
     */
    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return events;
    }

    /**
     * Get read-only view of domain events
     */
    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    /**
     * Clear all domain events without returning them
     */
    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
