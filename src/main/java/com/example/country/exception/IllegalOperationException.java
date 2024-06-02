package com.example.country.exception;

public abstract class IllegalOperationException extends Exception {
    public IllegalOperationException(String message) {
        super(message);
    }

    public abstract String getMessageSummary();
}
