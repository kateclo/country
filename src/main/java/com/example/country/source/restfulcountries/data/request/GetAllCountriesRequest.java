package com.example.country.source.restfulcountries.data.request;

import com.example.country.source.external.SourceApiRequest;

public class GetAllCountriesRequest extends SourceApiRequest {

    private static final String endPoint = "/countries";
    private String authToken;

    public GetAllCountriesRequest(String baseRequestUrl, String authToken) {
        super(baseRequestUrl);
        this.authToken = authToken;
    }

    @Override
    public String getCompleteUrl() {
        return baseRequestUrl + endPoint;
    }

    @Override
    public String authToken() {
        return authToken;
    }
}
