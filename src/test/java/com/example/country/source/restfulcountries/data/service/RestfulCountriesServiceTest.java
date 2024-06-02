package com.example.country.source.restfulcountries.data.service;

import com.example.country.controller.fixtures.CountryDtoFixture;
import com.example.country.controller.fixtures.CountryLiteDtoFixture;
import com.example.country.data.CountryDto;
import com.example.country.data.CountryLiteDto;
import com.example.country.source.external.WebClientService;
import com.example.country.source.restfulcountries.data.fixture.CountryLiteResponseFixture;
import com.example.country.source.restfulcountries.data.fixture.CountryResponseFixture;
import com.example.country.source.restfulcountries.data.request.GetAllCountriesRequest;
import com.example.country.source.restfulcountries.data.request.GetCountryByNameRequest;
import com.example.country.source.restfulcountries.data.response.CountriesDataResponse;
import com.example.country.source.restfulcountries.data.response.CountryByNameDataResponse;
import com.example.country.source.restfulcountries.data.response.CountryLiteResponse;
import com.example.country.source.restfulcountries.data.response.CountryResponse;
import com.example.country.source.restfulcountries.service.RestfulCountriesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestfulCountriesServiceTest {
    @InjectMocks
    RestfulCountriesService restfulCountriesService;

    @Mock
    WebClientService webClientService;

    @Test
    public void shouldReturnAllCountriesBasicInfo() {
        // Given
        CountryLiteResponse countryLiteResponse1 = CountryLiteResponseFixture.countryLiteResponse("Restful Country1", "Restful Code1");
        CountryLiteResponse countryLiteResponse2 = CountryLiteResponseFixture.countryLiteResponse("Restful Country2", "Restful Code2");

        CountryLiteDto dto1 = CountryLiteDtoFixture.countryLiteDto("Restful Country1", "Restful Code1");
        CountryLiteDto dto2 = CountryLiteDtoFixture.countryLiteDto("Restful Country2", "Restful Code2");

        CountriesDataResponse countriesDataResponse = new CountriesDataResponse();
        countriesDataResponse.setData(List.of(countryLiteResponse1, countryLiteResponse2));

        Mono<CountriesDataResponse> mono = Mono.just(countriesDataResponse);
        when(webClientService.fetchData(any(GetAllCountriesRequest.class), eq(CountriesDataResponse.class))).thenReturn(mono);

        Flux<CountryLiteDto> fluxResponse = restfulCountriesService.getAllCountriesBasicInfo();

        // Expect
        StepVerifier.create(fluxResponse)
                .expectNext(dto1)
                .expectNext(dto2)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void shouldHandleEmptyResultWhenRetrievingAllCountriesBasicInfo() {
        // Given
        Mono<CountriesDataResponse> mono = Mono.empty();
        when(webClientService.fetchData(any(GetAllCountriesRequest.class), eq(CountriesDataResponse.class))).thenReturn(mono);

        Flux<CountryLiteDto> fluxResponse = restfulCountriesService.getAllCountriesBasicInfo();

        // Expect
        StepVerifier.create(fluxResponse)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void shouldThrowExceptionWhenNullResultDuringRetrievalOfAllCountriesBasicInfo() {
        // Given
        Mono<CountriesDataResponse> mono = null;
        when(webClientService.fetchData(any(GetAllCountriesRequest.class), eq(CountriesDataResponse.class))).thenReturn(mono);

        assertThrows(NullPointerException.class, () -> restfulCountriesService.getAllCountriesBasicInfo());
    }


    @Test
    public void shouldHandleEmptyCountriesResultWhenRetrievingAllCountriesBasicInfo() {
        // Given
        CountriesDataResponse countriesDataResponse = new CountriesDataResponse();
        countriesDataResponse.setData(Collections.emptyList());

        Mono<CountriesDataResponse> mono = Mono.just(countriesDataResponse);
        when(webClientService.fetchData(any(GetAllCountriesRequest.class), eq(CountriesDataResponse.class))).thenReturn(mono);

        Flux<CountryLiteDto> fluxResponse = restfulCountriesService.getAllCountriesBasicInfo();

        // Expect
        StepVerifier.create(fluxResponse)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void shouldHandleNullCountriesResultWhenRetrievingAllCountriesBasicInfo() {
        // Given
        CountriesDataResponse countriesDataResponse = new CountriesDataResponse();
        countriesDataResponse.setData(null);

        Mono<CountriesDataResponse> mono = Mono.just(countriesDataResponse);
        when(webClientService.fetchData(any(GetAllCountriesRequest.class), eq(CountriesDataResponse.class))).thenReturn(mono);

        Flux<CountryLiteDto> fluxResponse = restfulCountriesService.getAllCountriesBasicInfo();

        // Expect
        StepVerifier.create(fluxResponse)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void shouldReturnCountryData() {
        // Given
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        CountryDto dto = CountryDtoFixture.countryDto(countryResponse);
        String countryName = countryResponse.getName();

        CountryByNameDataResponse countryByNameDataResponse = new CountryByNameDataResponse();
        countryByNameDataResponse.setData(countryResponse);

        Mono<CountryByNameDataResponse> mono = Mono.just(countryByNameDataResponse);
        when(webClientService.fetchData(any(GetCountryByNameRequest.class), eq((CountryByNameDataResponse.class)))).thenReturn(mono);

        Mono<CountryDto> monoResponse = restfulCountriesService.getCountryByName(countryName);

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
        String countryName = countryResponse.getName();

        Mono<CountryByNameDataResponse> mono = Mono.empty();
        when(webClientService.fetchData(any(GetCountryByNameRequest.class), eq((CountryByNameDataResponse.class)))).thenReturn(mono);

        Mono<CountryDto> monoResponse = restfulCountriesService.getCountryByName(countryName);

        // Expect
        StepVerifier.create(monoResponse)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void shouldThrowExceptionWhenNullResultDuringRetrievalOfCountryData() {
        // Given
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        String countryName = countryResponse.getName();

        Mono<CountryByNameDataResponse> mono = null;
        when(webClientService.fetchData(any(GetCountryByNameRequest.class), eq((CountryByNameDataResponse.class)))).thenReturn(mono);

        assertThrows(NullPointerException.class, () -> restfulCountriesService.getCountryByName(countryName));
    }

    @Test
    public void shouldHandleNullCountryResultWhenRetrievingCountryData() {
        // Given
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();
        String countryName = countryResponse.getName();

        CountryByNameDataResponse countryByNameDataResponse = new CountryByNameDataResponse();
        countryByNameDataResponse.setData(null);

        Mono<CountryByNameDataResponse> mono = Mono.just(countryByNameDataResponse);
        when(webClientService.fetchData(any(GetCountryByNameRequest.class), eq((CountryByNameDataResponse.class)))).thenReturn(mono);

        Mono<CountryDto> monoResponse = restfulCountriesService.getCountryByName(countryName);

        // Expect
        StepVerifier.create(monoResponse)
                .expectNextCount(0)
                .verifyComplete();
    }
}
