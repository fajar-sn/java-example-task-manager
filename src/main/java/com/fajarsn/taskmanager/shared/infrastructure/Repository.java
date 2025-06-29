package com.fajarsn.taskmanager.shared.infrastructure;

import com.fajarsn.taskmanager.shared.domain.Entity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Generic repository interface using Java generics.
 * Provides common CRUD operations for all entities.
 */
public interface Repository<T extends Entity, ID extends UUID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    List<T> findAll(int page, int size);
    boolean existById(ID id);
    long count();
    void deleteById(ID id);
    void delete(T entity);
}
