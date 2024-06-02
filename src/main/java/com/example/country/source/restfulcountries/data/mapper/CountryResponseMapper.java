package com.example.country.source.restfulcountries.data.mapper;

import com.example.country.data.CountryDto;
import com.example.country.source.restfulcountries.data.response.CountryResponse;
import com.example.country.source.restfulcountries.data.response.HrefResponse;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class CountryResponseMapper {

    private static final String COMMA_DELIMITER = ",";
    private static final String EMPTY_STRING = "";

    public static CountryDto toCountryDto(CountryResponse response) {
        if (response == null) {
            return null;
        }

        CountryDto dto = new CountryDto();
        dto.setName(response.getName());
        dto.setCountryCode(response.getIso2());
        dto.setCapital(response.getCapital());
        dto.setPopulation(getPopulation(response.getPopulation()));
        dto.setFlagFileUrl(getFlagUrl(response.getHref()));

        return dto;
    }

    private static String getFlagUrl(HrefResponse hrefResponse) {
        if (hrefResponse != null) {
            if (hrefResponse.getFlag() != null) {
                return hrefResponse.getFlag();
            }
        }

        return EMPTY_STRING;
    }

    private static long getPopulation(String populationInString) {
        if (StringUtil.isNullOrEmpty(populationInString)) {
            return 0;
        }

        String formattedValue = populationInString.trim().replaceAll(COMMA_DELIMITER, EMPTY_STRING);
        try {
            return Long.parseLong(formattedValue);
        } catch (NumberFormatException e) {
            try {
                BigDecimal bigDecimalValue = new BigDecimal(formattedValue);
                return bigDecimalValue.setScale(0, RoundingMode.HALF_UP).longValue();
            } catch (NumberFormatException ex) {
                log.warn(String.format("[RestCountries-Mapper] Population value is not a valid number: %s. Will default to zero.", formattedValue));
                return 0;
            }
        }
    }
}
