package com.example.country.source.external;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

public abstract class SourceApiRequest {

    @Getter
    @JsonIgnore
    protected String baseRequestUrl;

    public SourceApiRequest(String baseRequestUrl) {
        this.baseRequestUrl = baseRequestUrl;
    }

    public abstract String getCompleteUrl();

    public abstract String authToken();
}
