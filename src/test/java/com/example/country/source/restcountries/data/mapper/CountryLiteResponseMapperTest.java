package com.example.country.source.restcountries.data.mapper;

import com.example.country.data.CountryLiteDto;
import com.example.country.source.restcountries.data.fixture.CountryLiteResponseFixture;
import com.example.country.source.restcountries.data.response.CountryLiteResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CountryLiteResponseMapperTest {
    @Test
    public void shouldMapData() {
        CountryLiteResponse countryLiteResponse = CountryLiteResponseFixture.countryLiteResponse();

        CountryLiteDto dto = CountryLiteResponseMapper.toCountryLiteDto(countryLiteResponse);

        assertNotNull(dto);
        assertEquals(dto.getName(), "Common Name");
        assertEquals(dto.getCountryCode(), "Code");
    }

    @Test
    public void shouldMapDataWhenNameResponseIsNull() {
        CountryLiteResponse countryLiteResponse = CountryLiteResponseFixture.countryLiteResponse();
        countryLiteResponse.setName(null);

        CountryLiteDto dto = CountryLiteResponseMapper.toCountryLiteDto(countryLiteResponse);

        assertNotNull(dto);
        assertEquals(dto.getName(), "");
        assertEquals(dto.getCountryCode(), "Code");
    }

    @Test
    public void shouldMapDataWhenNameIsNull() {
        CountryLiteResponse countryLiteResponse = CountryLiteResponseFixture.countryLiteResponse();
        countryLiteResponse.getName().setCommon(null);

        CountryLiteDto dto = CountryLiteResponseMapper.toCountryLiteDto(countryLiteResponse);

        assertNotNull(dto);
        assertEquals(dto.getName(), "");
        assertEquals(dto.getCountryCode(), "Code");
    }

    @Test
    public void shouldMapDataWhenNameIsEmpty() {
        CountryLiteResponse countryLiteResponse = CountryLiteResponseFixture.countryLiteResponse();
        countryLiteResponse.getName().setCommon(null);

        CountryLiteDto dto = CountryLiteResponseMapper.toCountryLiteDto(countryLiteResponse);

        assertNotNull(dto);
        assertEquals(dto.getName(), "");
        assertEquals(dto.getCountryCode(), "Code");
    }

    @Test
    public void shouldReturnNullWhenResponseIsNull() {
        assertNull(CountryLiteResponseMapper.toCountryLiteDto(null));
    }
}
