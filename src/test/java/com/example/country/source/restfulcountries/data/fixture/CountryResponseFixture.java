package com.example.country.source.restfulcountries.data.fixture;

import com.example.country.source.restfulcountries.data.response.CountryResponse;

public class CountryResponseFixture {
    public static CountryResponse countryResponse() {
        CountryResponse countryResponse = new CountryResponse();
        countryResponse.setName("Restful Country Name");
        countryResponse.setIso2("Restful Country Code");
        countryResponse.setCapital("Restful Country Capital");
        countryResponse.setPopulation("20240602");
        countryResponse.setHref(HrefResponseFixture.hrefResponse());
        return countryResponse;
    }
}
