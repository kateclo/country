package com.example.country.source.restcountries.data.fixture;

import com.example.country.source.restcountries.data.response.CountryLiteResponse;

public class CountryLiteResponseFixture {
    public static CountryLiteResponse countryLiteResponse() {
        CountryLiteResponse countryLiteResponse = new CountryLiteResponse();
        countryLiteResponse.setName(NameResponseFixture.nameResponse());
        countryLiteResponse.setCca2("Code");
        return countryLiteResponse;
    }

    public static CountryLiteResponse countryLiteResponse(String name, String code) {
        CountryLiteResponse countryLiteResponse = new CountryLiteResponse();
        countryLiteResponse.setName(NameResponseFixture.nameResponse(name));
        countryLiteResponse.setCca2(code);
        return countryLiteResponse;
    }
}
