package com.fajarsn.taskmanager.shared.application;

/**
 * Base interface for Query handlers.
 *
 * @param <TQuery> The query type that extends Query
 * @param <TResponse> The response type that extends Response
 */
public interface QueryHandler<TQuery extends Query, TResponse extends Response> {
    TResponse handle(TQuery query);

    default String getQueryHandlerName() {
        return this.getClass().getSimpleName();
    }
}
