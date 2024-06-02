package com.example.country.source.restcountries.data.fixture;

import com.example.country.source.restcountries.data.response.CountryResponse;

import java.util.List;

public class CountryResponseFixture {
    public static CountryResponse countryResponse() {
        CountryResponse countryResponse = new CountryResponse();
        countryResponse.setName(NameResponseFixture.nameResponse());
        countryResponse.setFlags(FlagsResponseFixture.flagsResponse());
        countryResponse.setCapital(List.of("Capital 1", "Capital 2", "Capital 3"));
        countryResponse.setCca2("Code");
        countryResponse.setPopulation(1234567);
        return countryResponse;
    }
}
