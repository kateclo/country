package com.example.country.source.restcountries.data.mapper;

import com.example.country.data.CountryDto;
import com.example.country.source.restcountries.data.fixture.CountryResponseFixture;
import com.example.country.source.restcountries.data.response.CountryResponse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CountryResponseMapperTest {

    @Test
    public void shouldMapData() {
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();

        CountryDto countryDto = CountryResponseMapper.toCountryDto(countryResponse);

        assertNotNull(countryDto);
        assertEquals(countryDto.getName(), "Common Name");
        assertEquals(countryDto.getCountryCode(), "Code");
        assertEquals(countryDto.getCapital(), "Capital 1,Capital 2,Capital 3");
        assertEquals(countryDto.getFlagFileUrl(), "http://some.png.file.url");
        assertEquals(countryDto.getPopulation(), 1234567);
    }

    @Test
    public void shouldMapDataWhenFlagPngUrlIsEmpty() {
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        countryResponse.getFlags().setPng("");

        CountryDto countryDto = CountryResponseMapper.toCountryDto(countryResponse);

        assertEquals(countryDto.getFlagFileUrl(), "http://some.svg.file.url");
    }

    @Test
    public void shouldMapDataWhenFlagPngUrlIsNull() {
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        countryResponse.getFlags().setPng(null);

        CountryDto countryDto = CountryResponseMapper.toCountryDto(countryResponse);

        assertEquals(countryDto.getFlagFileUrl(), "http://some.svg.file.url");
    }

    @Test
    public void shouldMapDataWhenCapitalDataIsEmptyList() {
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        countryResponse.setCapital(new ArrayList<>());

        CountryDto countryDto = CountryResponseMapper.toCountryDto(countryResponse);

        assertEquals(countryDto.getCapital(), "");
    }

    @Test
    public void shouldMapDataWhenCapitalDataIsNull() {
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        countryResponse.setCapital(null);

        CountryDto countryDto = CountryResponseMapper.toCountryDto(countryResponse);

        assertEquals(countryDto.getCapital(), "");
    }

    @Test
    public void shouldMapDataWhenNameResponseIsNull() {
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        countryResponse.setName(null);

        CountryDto countryDto = CountryResponseMapper.toCountryDto(countryResponse);

        assertEquals(countryDto.getName(), "");
    }

    @Test
    public void shouldMapDataWhenNameIsNull() {
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        countryResponse.getName().setCommon(null);

        CountryDto countryDto = CountryResponseMapper.toCountryDto(countryResponse);

        assertEquals(countryDto.getName(), "");
    }

    @Test
    public void shouldMapDataWhenNameIsEmpty() {
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        countryResponse.getName().setCommon("");

        CountryDto countryDto = CountryResponseMapper.toCountryDto(countryResponse);

        assertEquals(countryDto.getName(), "");
    }

    @Test
    public void shouldReturnNullWhenResponseIsNull() {
        assertNull(CountryResponseMapper.toCountryDto(null));
    }
}
