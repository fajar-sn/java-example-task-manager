package com.fajarsn.taskmanager.shared.application;

import java.text.MessageFormat;

/**
 * Base interface for Query operations (read-only commands).
 * Queries extend Command since they are a special type of command.
 */
public interface Query extends Command {
    @Override
    default String getCommandType() {
        return MessageFormat.format("Query_{0}", this.getClass().getSimpleName());
    }
}
