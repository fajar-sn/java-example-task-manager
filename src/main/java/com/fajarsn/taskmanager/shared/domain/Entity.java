package com.fajarsn.taskmanager.shared.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Base class for all entities following DDD principles.
 * Implements commonly entity behavior and identity management.
 */
public abstract class Entity {
    protected final UUID id;
    protected final LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected int version;

    protected Entity() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.version = 0;
    }

    protected Entity(UUID id) {
        this.id = Objects.requireNonNull(id, "Entity ID cannot be null");
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.version = 0;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getVersion() {
        return version;
    }

    protected void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
        this.version++;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Entity entity = (Entity) obj;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s{id=%s, createdAt=%s, updatedAt=%s, version=%d}", getClass().getSimpleName(), id, createdAt, updatedAt, version);
    }
}
