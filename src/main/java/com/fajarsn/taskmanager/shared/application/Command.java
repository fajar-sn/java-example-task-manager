package com.fajarsn.taskmanager.shared.application;

/**
 * Marker interface for Command operations (CQRS pattern).
 * Commands are write operations that modify state.
 */
public interface Command<TRequest, TResponse> {
    TResponse execute(TRequest request);
}
