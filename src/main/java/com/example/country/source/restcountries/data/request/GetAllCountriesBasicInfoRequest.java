package com.example.country.source.restcountries.data.request;

import com.example.country.source.external.SourceApiRequest;

public class GetAllCountriesBasicInfoRequest extends SourceApiRequest {

    private static final String endPoint = "/all?fields=name,cca2";

    public GetAllCountriesBasicInfoRequest(String baseRequestUrl) {
        super(baseRequestUrl);
    }

    @Override
    public String getCompleteUrl() {
        return baseRequestUrl + endPoint;
    }

    @Override
    public String authToken() {
        return null;
    }
}
