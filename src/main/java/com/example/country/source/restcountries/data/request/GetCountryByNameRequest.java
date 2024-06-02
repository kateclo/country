package com.example.country.source.restcountries.data.request;

import com.example.country.source.external.SourceApiRequest;

public class GetCountryByNameRequest extends SourceApiRequest {

    private static final String endPoint = "/name/%s";

    private String countryName;

    public GetCountryByNameRequest(String baseRequestUrl, String countryName) {
        super(baseRequestUrl);
        this.countryName = countryName;
    }

    @Override
    public String getCompleteUrl() {
        return baseRequestUrl + String.format(endPoint, countryName);
    }

    @Override
    public String authToken() {
        return null;
    }
}
