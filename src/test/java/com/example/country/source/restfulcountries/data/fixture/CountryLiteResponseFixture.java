package com.example.country.source.restfulcountries.data.fixture;


import com.example.country.source.restfulcountries.data.response.CountryLiteResponse;

public class CountryLiteResponseFixture {
    public static CountryLiteResponse countryLiteResponse() {
        CountryLiteResponse response = new CountryLiteResponse();
        response.setName("Restful Country Name");
        response.setIso2("Restful Country Code");
        return response;
    }

    public static CountryLiteResponse countryLiteResponse(String name, String code) {
        CountryLiteResponse response = new CountryLiteResponse();
        response.setName(name);
        response.setIso2(code);
        return response;
    }
}
