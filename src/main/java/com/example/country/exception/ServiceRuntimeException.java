package com.example.country.exception;

public abstract class ServiceRuntimeException extends RuntimeException {
    public ServiceRuntimeException(String message) {
        super(message);
    }

    public abstract String getMessageSummary();
}
