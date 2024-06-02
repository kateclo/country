package com.example.country.source.restcountries;

import com.example.country.controller.fixtures.CountryDtoFixture;
import com.example.country.controller.fixtures.CountryLiteDtoFixture;
import com.example.country.data.CountryDto;
import com.example.country.data.CountryLiteDto;
import com.example.country.source.restcountries.service.RestCountriesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestCountriesApiTest {
    @InjectMocks
    RestCountriesApi restCountriesApi;

    @Mock
    RestCountriesService restCountriesService;

    @Test
    public void shouldReturnAllCountriesBasicInfo() {
        // Given
        CountryLiteDto dto1 = CountryLiteDtoFixture.countryLiteDto("Country1", "Code2");
        CountryLiteDto dto2 = CountryLiteDtoFixture.countryLiteDto("Country2", "Code2");

        Flux<CountryLiteDto> flux = Flux.just(dto1, dto2);
        when(restCountriesService.getAllCountriesBasicInfo()).thenReturn(flux);

        Flux<CountryLiteDto> fluxResponse = restCountriesApi.getAllCountriesBasicInfo();

        // Expect
        StepVerifier.create(fluxResponse)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void shouldReturnCountryData() {
        // Given
        String countryName = "Country Name";
        CountryDto dto = CountryDtoFixture.countryDto();

        Mono<CountryDto> mono = Mono.just(dto);
        when(restCountriesService.getCountryByName(countryName)).thenReturn(mono);

        Mono<CountryDto> response = restCountriesApi.getCountryByName(countryName);

        // Expect
        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();
    }
}
