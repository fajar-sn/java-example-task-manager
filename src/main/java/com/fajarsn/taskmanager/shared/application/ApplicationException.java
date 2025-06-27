package com.fajarsn.taskmanager.shared.application;

/**
 * Base exception for application layer errors.
 * Represents use case failures and application-specific errors.
 */
public class ApplicationException extends RuntimeException {
    private final String errorCode;

    public ApplicationException(String message) {
        super(message);
        this.errorCode = "APPLICATION_ERROR";
    }

    public ApplicationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "APPLICATION_ERROR";
    }

    public ApplicationException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
