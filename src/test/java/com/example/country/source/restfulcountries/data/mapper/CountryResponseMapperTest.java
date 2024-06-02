package com.example.country.source.restfulcountries.data.mapper;

import com.example.country.data.CountryDto;
import com.example.country.source.restfulcountries.data.fixture.CountryResponseFixture;
import com.example.country.source.restfulcountries.data.response.CountryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CountryResponseMapperTest {
    @Test
    public void shouldMapData() {
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();

        CountryDto countryDto = CountryResponseMapper.toCountryDto(countryResponse);

        assertNotNull(countryDto);
        assertEquals(countryDto.getName(), "Restful Country Name");
        assertEquals(countryDto.getCountryCode(), "Restful Country Code");
        assertEquals(countryDto.getCapital(), "Restful Country Capital");
        assertEquals(countryDto.getFlagFileUrl(), "http://restful/some/flag/url");
        assertEquals(countryDto.getPopulation(), 20240602);
    }

    @Test
    public void shouldMapDataWhenHrefResponseIsNull() {
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        countryResponse.setHref(null);

        CountryDto countryDto = CountryResponseMapper.toCountryDto(countryResponse);
        assertEquals(countryDto.getFlagFileUrl(), "");
    }

    @Test
    public void shouldMapDataWhenFlagIsNull() {
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        countryResponse.getHref().setFlag(null);

        CountryDto countryDto = CountryResponseMapper.toCountryDto(countryResponse);
        assertEquals(countryDto.getFlagFileUrl(), "");
    }

    @Test
    public void shouldMapDataWhenFlagIsEmpty() {
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        countryResponse.getHref().setFlag("");

        CountryDto countryDto = CountryResponseMapper.toCountryDto(countryResponse);
        assertEquals(countryDto.getFlagFileUrl(), "");
    }

    @Test
    public void shouldMapDataWhenPopulationIsNull() {
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        countryResponse.setPopulation(null);

        CountryDto countryDto = CountryResponseMapper.toCountryDto(countryResponse);
        assertEquals(countryDto.getPopulation(), 0);
    }

    @ParameterizedTest(name = "{index} => input={0}, expOutput={1}")
    @CsvSource({
            // valid values
            "'20240602', 20240602",
            "'20,240,602', 20240602",

            // invalid values
            "'100.00', 100",                            // decimal
            "'-100', -100",                             // negative value
            "'-100.50', -101",                          // negative decimal value, rounded up
            "'100.50', 101",                            // decimal - rounded up
            "'100.90', 101",                            // decimal - rounded up
            "'100.40', 100",                            // decimal - rounded down

            // invalid values - 2
            "'', 0",                                    // empty
            "'         ', 0",                           // empty with trailing spaces
            "',,,,20,,,,,240,,,,,602,,,', 20240602",    // with commas
            "'   20,240,602    ', 20240602",            // trailing spaces
            "'20,   240,    602', 0",                   // with spaces in between
            "'100.9,0', 101",                           // with commas in a decimal
            "'1,0,0.9,0', 101",                         // with commas in a decimal
            "'abcdefg', 0",                             // non-numeric
            "'abc defg', 0"                             // non-numeric
    })
    public void shouldMapDataForVaryingPopulationValues(String input, long expOutput) {
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        countryResponse.setPopulation(input);

        CountryDto countryDto = CountryResponseMapper.toCountryDto(countryResponse);
        assertEquals(countryDto.getPopulation(), expOutput);
    }
}
