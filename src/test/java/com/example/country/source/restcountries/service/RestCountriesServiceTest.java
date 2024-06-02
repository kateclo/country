package com.example.country.source.restcountries.service;

import com.example.country.controller.fixtures.CountryDtoFixture;
import com.example.country.controller.fixtures.CountryLiteDtoFixture;
import com.example.country.data.CountryDto;
import com.example.country.data.CountryLiteDto;
import com.example.country.source.external.WebClientService;
import com.example.country.source.restcountries.data.fixture.CountryLiteResponseFixture;
import com.example.country.source.restcountries.data.fixture.CountryResponseFixture;
import com.example.country.source.restcountries.data.request.GetAllCountriesBasicInfoRequest;
import com.example.country.source.restcountries.data.request.GetCountryByNameRequest;
import com.example.country.source.restcountries.data.response.CountryLiteResponse;
import com.example.country.source.restcountries.data.response.CountryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestCountriesServiceTest {
    @InjectMocks
    RestCountriesService restCountriesService;

    @Mock
    WebClientService webClientService;

    @Test
    public void shouldReturnAllCountriesBasicInfo() {
        // Given
        CountryLiteResponse countryLiteResponse1 = CountryLiteResponseFixture.countryLiteResponse("Country 1", "Code 1");
        CountryLiteResponse countryLiteResponse2 = CountryLiteResponseFixture.countryLiteResponse("Country 2", "Code 2");
        CountryLiteResponse countryLiteResponse3 = CountryLiteResponseFixture.countryLiteResponse("Country 3", "Code 3");

        CountryLiteDto dto1 = CountryLiteDtoFixture.countryLiteDto("Country 1", "Code 1");
        CountryLiteDto dto2 = CountryLiteDtoFixture.countryLiteDto("Country 2", "Code 2");
        CountryLiteDto dto3 = CountryLiteDtoFixture.countryLiteDto("Country 3", "Code 3");

        Flux<CountryLiteResponse> flux = Flux.just(countryLiteResponse1, countryLiteResponse2, countryLiteResponse3);
        when(webClientService.fetchMultiData(any(GetAllCountriesBasicInfoRequest.class), eq(CountryLiteResponse.class))).thenReturn(flux);

        Flux<CountryLiteDto> fluxResponse = restCountriesService.getAllCountriesBasicInfo();

        // Expect
        StepVerifier.create(fluxResponse)
                .expectNext(dto1)
                .expectNext(dto2)
                .expectNext(dto3)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void shouldHandleEmptyResultWhenRetrievingAllCountriesBasicInfo() {
        // Given
        Flux<CountryLiteResponse> flux = Flux.empty();
        when(webClientService.fetchMultiData(any(GetAllCountriesBasicInfoRequest.class) , eq(CountryLiteResponse.class))).thenReturn(flux);

        Flux<CountryLiteDto> fluxResponse = restCountriesService.getAllCountriesBasicInfo();

        // Expect
        StepVerifier.create(fluxResponse)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void shouldThrowExceptionWhenNullResultDuringRetrievalOfAllCountriesBasicInfo() {
        // Given
        Flux<CountryLiteResponse> flux = null;
        when(webClientService.fetchMultiData(any(GetAllCountriesBasicInfoRequest.class) , eq(CountryLiteResponse.class))).thenReturn(flux);

        assertThrows(NullPointerException.class, () -> restCountriesService.getAllCountriesBasicInfo());
    }

    @Test
    public void shouldReturnCountryData() {
        // Given

        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        CountryDto dto = CountryDtoFixture.countryDto(countryResponse);
        String countryName = countryResponse.getName().getCommon();

        Mono<CountryResponse> mono = Mono.just(countryResponse);
        when(webClientService.fetchData(any(GetCountryByNameRequest.class), eq(CountryResponse.class))).thenReturn(mono);

        Mono<CountryDto> monoResponse = restCountriesService.getCountryByName(countryName);

        // Expect
        StepVerifier.create(monoResponse)
                .expectNext(dto)
                .expectNextCount(0)
                .verifyComplete();

    }

    @Test
    public void shouldHandleEmptyResultWhenRetrievingCountryData() {
        // Given
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        String countryName = countryResponse.getName().getCommon();

        Mono<CountryResponse> mono = Mono.empty();
        when(webClientService.fetchData(any(GetCountryByNameRequest.class), eq(CountryResponse.class))).thenReturn(mono);

        Mono<CountryDto> monoResponse = restCountriesService.getCountryByName(countryName);

        // Expect
        StepVerifier.create(monoResponse)
                .expectNextCount(0)
                .verifyComplete();

    }

    @Test
    public void shouldThrowExceptionWhenNullResultDuringRetrievalOfCountryData() {
        // Given
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        String countryName = countryResponse.getName().getCommon();

        Mono<CountryResponse> mono = null;
        when(webClientService.fetchData(any(GetCountryByNameRequest.class), eq(CountryResponse.class))).thenReturn(mono);

        assertThrows(NullPointerException.class, () -> restCountriesService.getCountryByName(countryName));
    }

}
