package com.example.country.exception;

public class UnsupportedSourceException extends IllegalOperationException {
    private static final String messageSummary = "Source code not supported";
    public UnsupportedSourceException(String code) {
        super(String.format("%s : %s", messageSummary, code));
    }

    @Override
    public String getMessageSummary() {
        return messageSummary;
    }
}