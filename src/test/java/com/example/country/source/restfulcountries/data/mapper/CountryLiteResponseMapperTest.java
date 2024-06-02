package com.example.country.source.restfulcountries.data.mapper;

import com.example.country.data.CountryLiteDto;
import com.example.country.source.restfulcountries.data.response.CountryLiteResponse;
import com.example.country.source.restfulcountries.data.fixture.CountryLiteResponseFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CountryLiteResponseMapperTest {
    @Test
    public void shouldMapData() {
        CountryLiteResponse countryLiteResponse = CountryLiteResponseFixture.countryLiteResponse();

        CountryLiteDto dto = CountryLiteResponseMapper.toCountryLiteDto(countryLiteResponse);

        assertNotNull(dto);
        assertEquals(dto.getName(), "Restful Country Name");
        assertEquals(dto.getCountryCode(), "Restful Country Code");
    }

    @Test
    public void shouldReturnNullWhenResponseIsNull() {
        assertNull(CountryLiteResponseMapper.toCountryLiteDto(null));
    }
}
