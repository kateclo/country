package com.example.country.controller.fixtures;

import com.example.country.data.CountryLiteDto;

public class CountryLiteDtoFixture {
    public static CountryLiteDto countryLiteDto(String name, String code) {
        CountryLiteDto dto = new CountryLiteDto();
        dto.setName(name);
        dto.setCountryCode(code);
        return dto;
    }
}
