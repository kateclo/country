package com.example.country.controller.fixtures;

import com.example.country.data.CountryDto;
import com.example.country.source.restcountries.data.response.CountryResponse;

public class CountryDtoFixture {
    private static final String COMMA_DELIMITER = ",";
    public static CountryDto countryDto() {
        CountryDto dto = new CountryDto();
        dto.setName("Name");
        dto.setCountryCode("Code");
        dto.setCapital("Capital");
        dto.setFlagFileUrl("http://some.url.com");
        dto.setPopulation(1234567);
        return dto;
    }

    public static CountryDto countryDto(CountryResponse countryResponse) {
        CountryDto dto = new CountryDto();
        dto.setName(countryResponse.getName().getCommon());
        dto.setCountryCode(countryResponse.getCca2());
        dto.setCapital(String.join(COMMA_DELIMITER, countryResponse.getCapital()));
        dto.setFlagFileUrl(countryResponse.getFlags().getPng());
        dto.setPopulation(countryResponse.getPopulation());
        return dto;
    }

    public static CountryDto countryDto(com.example.country.source.restfulcountries.data.response.CountryResponse countryResponse) {
        CountryDto dto = new CountryDto();
        dto.setName(countryResponse.getName());
        dto.setCountryCode(countryResponse.getIso2());
        dto.setCapital(countryResponse.getCapital());
        dto.setFlagFileUrl(countryResponse.getHref().getFlag());
        dto.setPopulation(Long.parseLong(countryResponse.getPopulation().trim().replaceAll(",", "")));
        return dto;
    }
}
