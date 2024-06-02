package com.example.country.source.restfulcountries.data;

import com.example.country.controller.fixtures.CountryDtoFixture;
import com.example.country.controller.fixtures.CountryLiteDtoFixture;
import com.example.country.data.CountryDto;
import com.example.country.data.CountryLiteDto;
import com.example.country.source.restfulcountries.RestfulCountriesApi;
import com.example.country.source.restfulcountries.service.RestfulCountriesService;
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
public class RestfulCountriesApiTest {
    @InjectMocks
    RestfulCountriesApi restfulCountriesApi;

    @Mock
    RestfulCountriesService restfulCountriesService;

    @Test
    public void shouldReturnAllCountriesBasicInfo() {
        // Given
        CountryLiteDto dto1 = CountryLiteDtoFixture.countryLiteDto("Restful Country1", "Restful Code1");
        CountryLiteDto dto2 = CountryLiteDtoFixture.countryLiteDto("Restful Country2", "Restful Code2");
        CountryLiteDto dto3 = CountryLiteDtoFixture.countryLiteDto("Restful Country3", "Restful Code3");

        Flux<CountryLiteDto> flux = Flux.just(dto1, dto2, dto3);
        when(restfulCountriesService.getAllCountriesBasicInfo()).thenReturn(flux);

        Flux<CountryLiteDto> fluxResponse = restfulCountriesService.getAllCountriesBasicInfo();

        // Expect
        StepVerifier.create(fluxResponse)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void shouldReturnCountryData() {
        // Given
        String countryName = "Restful Country Name";
        CountryDto dto = CountryDtoFixture.countryDto();

        Mono<CountryDto> mono = Mono.just(dto);
        when(restfulCountriesService.getCountryByName(countryName)).thenReturn(mono);


        Mono<CountryDto> monoResponse = restfulCountriesApi.getCountryByName(countryName);
        // Expect
        StepVerifier.create(monoResponse)
                .expectNextCount(1)
                .verifyComplete();
    }
}
