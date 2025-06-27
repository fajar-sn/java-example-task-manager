package com.fajarsn.taskmanager.shared.application;

/**
 * Marker interface for Use Cases in Clean Architecture.
 * Use cases represent application specific business rules.
 */
public interface UseCase<TRequest, TResponse> {
    TResponse execute(TRequest request);
}
