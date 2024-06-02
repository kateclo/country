package com.example.country.source.restcountries.data.mapper;

import com.example.country.data.CountryLiteDto;
import com.example.country.source.restcountries.data.response.CountryLiteResponse;
import com.example.country.source.restcountries.data.response.NameResponse;

public class CountryLiteResponseMapper {
    private static final String EMPTY_STRING = "";
    public static CountryLiteDto toCountryLiteDto(CountryLiteResponse response) {
        if (response == null) {
            return null;
        }

        CountryLiteDto dto = new CountryLiteDto();
        dto.setName(getName(response.getName()));
        dto.setCountryCode(response.getCca2());

        return dto;
    }

    private static String getName(NameResponse nameResponse) {
        if (nameResponse != null) {
            if (nameResponse.getCommon() != null) {
                return nameResponse.getCommon();
            }
        }
        return EMPTY_STRING;
    }
}
