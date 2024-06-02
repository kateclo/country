package com.example.country.source.restcountries.data.mapper;

import com.example.country.data.CountryDto;
import com.example.country.source.restcountries.data.response.CountryResponse;
import com.example.country.source.restcountries.data.response.FlagsResponse;
import com.example.country.source.restcountries.data.response.NameResponse;
import io.netty.util.internal.StringUtil;

import java.util.List;


public class CountryResponseMapper {
    private static final String COMMA_DELIMITER = ",";
    private static final String EMPTY_STRING = "";

    public static CountryDto toCountryDto(CountryResponse response) {
        if (response == null) {
            return null;
        }

        CountryDto dto = new CountryDto();
        dto.setName(getName(response.getName()));
        dto.setCountryCode(response.getCca2());
        dto.setCapital(getFormattedCapital(response.getCapital()));
        dto.setPopulation(response.getPopulation());
        dto.setFlagFileUrl(getFlagUrl(response.getFlags()));

        return dto;
    }

    private static String getFlagUrl(FlagsResponse response) {
        if (response != null) {
            if (!StringUtil.isNullOrEmpty(response.getPng())) {
                return response.getPng();
            } else if (!StringUtil.isNullOrEmpty(response.getSvg())) {
                return response.getSvg();
            }
        }

        return EMPTY_STRING;
    }
    private static String getFormattedCapital(List<String> capital) {
        if (capital != null) {
            return String.join(COMMA_DELIMITER, capital);
        } else {
            return EMPTY_STRING;
        }
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
