package com.example.country.source.restfulcountries.data.request;

import com.example.country.source.external.SourceApiRequest;

public class GetCountryByNameRequest extends SourceApiRequest {

    private static final String endPoint = "/countries/%s";

    private String countryName;
    private String authToken;

    public GetCountryByNameRequest(String baseRequestUrl, String authToken, String countryName) {
        super(baseRequestUrl);
        this.authToken = authToken;
        this.countryName = countryName;
    }

    @Override
    public String getCompleteUrl() {
        return baseRequestUrl + String.format(endPoint, countryName);
    }

    @Override
    public String authToken() {
        return authToken;
    }
}
