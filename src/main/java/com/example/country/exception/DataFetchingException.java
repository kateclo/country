package com.example.country.exception;


public class DataFetchingException extends ServiceRuntimeException {

    private static final String messageSummary = "Encountered error while fetching data for the request";

    public DataFetchingException(String additionalInfo) {
        super(String.format("%s : %s", messageSummary, additionalInfo));

    }

    @Override
    public String getMessageSummary() {
        return messageSummary;
    }
}
