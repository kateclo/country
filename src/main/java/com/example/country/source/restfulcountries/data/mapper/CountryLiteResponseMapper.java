package com.example.country.source.restfulcountries.data.mapper;

import com.example.country.data.CountryLiteDto;
import com.example.country.source.restfulcountries.data.response.CountryLiteResponse;

public class CountryLiteResponseMapper {

    public static CountryLiteDto toCountryLiteDto(CountryLiteResponse response) {
        if (response == null) {
            return null;
        }

        CountryLiteDto dto = new CountryLiteDto();
        dto.setName(response.getName());
        dto.setCountryCode(response.getIso2());

        return dto;
    }
}

