package com.fajarsn.taskmanager.shared.application;

/**
 * Marker interface for Use Cases in Clean Architecture.
 * Use cases represent application specific business rules.
 */
public interface UseCase<TCommand extends Command, TResponse extends Response> {
    TResponse execute(TCommand request);

    /**
     * Get use case name for logging/auditing.
     */
    default String getUseCaseName() {
        return this.getClass().getSimpleName();
    }
}
