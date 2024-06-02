package com.example.country.service;

import com.example.country.controller.fixtures.CountryDtoFixture;
import com.example.country.controller.fixtures.CountryLiteDtoFixture;
import com.example.country.data.CountryDto;
import com.example.country.data.CountryLiteDto;
import com.example.country.exception.UnsupportedSourceException;
import com.example.country.source.restcountries.RestCountriesApi;
import com.example.country.source.restfulcountries.RestfulCountriesApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {

    @InjectMocks
    CountryService countryService;

    @Mock
    RestCountriesApi restApi;

    @Mock
    RestfulCountriesApi restfulApi;

    @Test
    public void shouldUseRestCountriesApiWhenRetrievingAllCountries() throws UnsupportedSourceException {
        // Given
        ReflectionTestUtils.setField(countryService, "source", "REST_COUNTRIES");
        ReflectionTestUtils.setField(countryService, "countryApiList", List.of(restfulApi, restApi));

        CountryLiteDto dto1 = CountryLiteDtoFixture.countryLiteDto("Country1", "Code2");
        CountryLiteDto dto2 = CountryLiteDtoFixture.countryLiteDto("Country2", "Code2");
        CountryLiteDto dto3 = CountryLiteDtoFixture.countryLiteDto("Country3", "Code3");

        Flux<CountryLiteDto> flux = Flux.just(dto1, dto2, dto3);

        when(restApi.getCode()).thenReturn("REST_COUNTRIES");
        when(restApi.getAllCountriesBasicInfo()).thenReturn(flux);

        Flux<CountryLiteDto> fluxResponse = countryService.getAllCountries();

        // Expect
        StepVerifier.create(fluxResponse)
                .expectNextCount(3)
                .verifyComplete();

        verify(restfulApi, times(0)).getAllCountriesBasicInfo();
    }

    @Test
    public void shouldUseRestfulCountriesApiWhenRetrievingAllCountries() throws UnsupportedSourceException {
        // Given
        ReflectionTestUtils.setField(countryService, "source", "RESTFUL_COUNTRIES");
        ReflectionTestUtils.setField(countryService, "countryApiList", List.of(restfulApi, restApi));

        CountryLiteDto dto1 = CountryLiteDtoFixture.countryLiteDto("Country1", "Code2");
        CountryLiteDto dto2 = CountryLiteDtoFixture.countryLiteDto("Country2", "Code2");
        CountryLiteDto dto3 = CountryLiteDtoFixture.countryLiteDto("Country3", "Code3");

        Flux<CountryLiteDto> flux = Flux.just(dto1, dto2, dto3);

        when(restfulApi.getCode()).thenReturn("RESTFUL_COUNTRIES");
        when(restfulApi.getAllCountriesBasicInfo()).thenReturn(flux);

        Flux<CountryLiteDto> fluxResponse = countryService.getAllCountries();

        // Expect
        StepVerifier.create(fluxResponse)
                .expectNextCount(3)
                .verifyComplete();

        verify(restApi, times(0)).getAllCountriesBasicInfo();
    }

    @Test
    public void shouldReturnUnsupportedSourceExceptionWhenRetrievingAllCountries() {
        // Given
        ReflectionTestUtils.setField(countryService, "source", "XXXX");
        ReflectionTestUtils.setField(countryService, "countryApiList", List.of(restfulApi, restApi));

        when(restApi.getCode()).thenReturn("REST_COUNTRIES");
        when(restfulApi.getCode()).thenReturn("RESTFUL_COUNTRIES");

        UnsupportedSourceException exception = assertThrows(UnsupportedSourceException.class, () -> countryService.getAllCountries());

        // Expect
        assertEquals("Source code not supported", exception.getMessageSummary());

        verify(restApi, times(0)).getAllCountriesBasicInfo();
        verify(restfulApi, times(0)).getAllCountriesBasicInfo();
    }

    @Test
    public void shouldReturnUnsupportedSourceExceptionWhenRetrievingAllCountriesAndApiListIsEmpty() {
        // Given
        ReflectionTestUtils.setField(countryService, "source", "XXXX");
        ReflectionTestUtils.setField(countryService, "countryApiList", new ArrayList<>());

        UnsupportedSourceException exception = assertThrows(UnsupportedSourceException.class, () -> countryService.getAllCountries());

        // Expect
        assertEquals("Source code not supported", exception.getMessageSummary());

        verify(restApi, times(0)).getAllCountriesBasicInfo();
        verify(restfulApi, times(0)).getAllCountriesBasicInfo();
    }

    @Test
    public void shouldUseRestCountriesApiWhenRetrievingCountryData() throws UnsupportedSourceException {
        // Given
        String countryName = "Country Name";
        ReflectionTestUtils.setField(countryService, "source", "REST_COUNTRIES");
        ReflectionTestUtils.setField(countryService, "countryApiList", List.of(restfulApi, restApi));

        CountryDto dto = CountryDtoFixture.countryDto();
        Mono<CountryDto> mono = Mono.just(dto);

        when(restApi.getCode()).thenReturn("REST_COUNTRIES");
        when(restApi.getCountryByName(countryName)).thenReturn(mono);

        Mono<CountryDto> response = countryService.getCountryByName(countryName);

        // Expect
        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        verify(restfulApi, times(0)).getCountryByName(countryName);
    }

    @Test
    public void shouldUseRestfulCountriesApiWhenRetrievingCountryData() throws UnsupportedSourceException {
        // Given
        String countryName = "Country Name";
        ReflectionTestUtils.setField(countryService, "source", "RESTFUL_COUNTRIES");
        ReflectionTestUtils.setField(countryService, "countryApiList", List.of(restfulApi, restApi));

        CountryDto dto = CountryDtoFixture.countryDto();
        Mono<CountryDto> mono = Mono.just(dto);

        when(restfulApi.getCode()).thenReturn("RESTFUL_COUNTRIES");
        when(restfulApi.getCountryByName(countryName)).thenReturn(mono);

        Mono<CountryDto> response = countryService.getCountryByName(countryName);

        // Expect
        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        verify(restApi, times(0)).getCountryByName(countryName);
    }

    @Test
    public void shouldReturnUnsupportedSourceExceptionWhenRetrievingCountryData() {
        // Given
        String countryName = "Country Name";
        ReflectionTestUtils.setField(countryService, "source", "XXXX");
        ReflectionTestUtils.setField(countryService, "countryApiList", List.of(restfulApi, restApi));

        when(restApi.getCode()).thenReturn("REST_COUNTRIES");
        when(restfulApi.getCode()).thenReturn("RESTFUL_COUNTRIES");

        UnsupportedSourceException exception = assertThrows(UnsupportedSourceException.class, () -> countryService.getCountryByName(countryName));

        // Expect
        assertEquals("Source code not supported", exception.getMessageSummary());

        verify(restApi, times(0)).getCountryByName(countryName);
        verify(restfulApi, times(0)).getCountryByName(countryName);
    }

    @Test
    public void shouldReturnUnsupportedSourceExceptionWhenRetrievingCountryDataAndApiListIsEmpty() {
        // Given
        String countryName = "Country Name";
        ReflectionTestUtils.setField(countryService, "source", "XXXX");
        ReflectionTestUtils.setField(countryService, "countryApiList", new ArrayList<>());

        UnsupportedSourceException exception = assertThrows(UnsupportedSourceException.class, () -> countryService.getCountryByName(countryName));

        // Expect
        assertEquals("Source code not supported", exception.getMessageSummary());

        verify(restApi, times(0)).getCountryByName(countryName);
        verify(restfulApi, times(0)).getCountryByName(countryName);
    }
}
