package com.example.country.controller;

import com.example.country.controller.fixtures.CountryDtoFixture;
import com.example.country.controller.fixtures.CountryLiteDtoFixture;
import com.example.country.data.CountryDto;
import com.example.country.data.CountryLiteDto;
import com.example.country.exception.DataFetchingException;
import com.example.country.exception.UnsupportedSourceException;
import com.example.country.service.CountryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(CountryController.class)
public class CountryControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    CountryService countryService;

    @Test
    public void shouldReturnCountryData() throws UnsupportedSourceException {
        // Given
        String countryName = "Name";
        CountryDto dto = CountryDtoFixture.countryDto();
        Mono<CountryDto> mono = Mono.just(dto);

        Mockito.when(countryService.getCountryByName(Mockito.anyString())).thenReturn(mono);

        // Expect
        webTestClient.get()
                .uri("/api/example/countries/name", countryName)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CountryDto.class)
                .hasSize(1)
                .contains(dto);
    }

    @Test
    public void shouldHandleEmptyCountryData() throws UnsupportedSourceException {
        // Given
        String countryName = "Name";
        Mockito.when(countryService.getCountryByName(Mockito.anyString())).thenReturn(Mono.empty());

        // Expect
        webTestClient.get()
                .uri("/api/example/countries/name", countryName)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(String.class)
                .isEqualTo(null);
    }

    @Test
    public void shouldHandleUnsupportedExceptionWhenRetrievingCountryData() throws UnsupportedSourceException {
        // Given
        String countryName = "Name";
        Mockito.when(countryService.getCountryByName(Mockito.anyString())).thenThrow(new UnsupportedSourceException("Invalid Code"));

        // Expect
        webTestClient.get()
                .uri("/api/example/countries/name", countryName)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .isEqualTo("Source code not supported");
    }

    @Test
    public void shouldHandleServiceRuntimeExceptionWhenRetrievingCountryData() throws UnsupportedSourceException {
        // Given
        String countryName = "Name";
        Mockito.when(countryService.getCountryByName(Mockito.anyString())).thenThrow(new DataFetchingException("Incorrect data"));

        // Expect
        webTestClient.get()
                .uri("/api/example/countries/name", countryName)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .isEqualTo("Encountered error while fetching data for the request");
    }

    @Test
    public void shouldHandleOtherExceptionWhenRetrievingCountryData() throws UnsupportedSourceException {
        // Given
        String countryName = "Name";
        Mockito.when(countryService.getCountryByName(Mockito.anyString())).thenThrow(new NullPointerException("Null pointer exception"));

        // Expect
        webTestClient.get()
                .uri("/api/example/countries/name", countryName)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.SERVICE_UNAVAILABLE)
                .expectBody(String.class)
                .isEqualTo("An unexpected exception was encountered");
    }

    @Test
    public void shouldReturnAllCountryData() throws UnsupportedSourceException {
        // Given
        CountryLiteDto dto1 = CountryLiteDtoFixture.countryLiteDto("Country1", "Code2");
        CountryLiteDto dto2 = CountryLiteDtoFixture.countryLiteDto("Country2", "Code2");
        CountryLiteDto dto3 = CountryLiteDtoFixture.countryLiteDto("Country3", "Code3");

        Flux<CountryLiteDto> flux = Flux.just(dto1, dto2, dto3);

        Mockito.when(countryService.getAllCountries()).thenReturn(flux);

        // Expect
        webTestClient.get()
                .uri("/api/example/countries")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CountryLiteDto.class)
                .hasSize(3)
                .contains(dto1, dto2, dto3);

    }

    @Test
    public void shouldHandleUnsupportedExceptionWhenRetrievingAllCountryData() throws UnsupportedSourceException {
        // Given
        Mockito.when(countryService.getAllCountries()).thenThrow(new UnsupportedSourceException("Invalid Code"));

        // Expect
        webTestClient.get()
                .uri("/api/example/countries")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .isEqualTo("Source code not supported");


    }

    @Test
    public void shouldHandleServiceRuntimeExceptionWhenRetrievingAllCountryData() throws UnsupportedSourceException {
        // Given
        Mockito.when(countryService.getAllCountries()).thenThrow(new DataFetchingException("Invalid Code 101"));

        // Expect
        webTestClient.get()
                .uri("/api/example/countries")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .isEqualTo("Encountered error while fetching data for the request");


    }

    @Test
    public void shouldHandleOtherExceptionWhenRetrievingAllCountryData() throws UnsupportedSourceException {
        // Given
        Mockito.when(countryService.getAllCountries()).thenThrow(new NullPointerException("Some exception"));

        // Expect
        webTestClient.get()
                .uri("/api/example/countries")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.SERVICE_UNAVAILABLE)
                .expectBody(String.class)
                .isEqualTo("An unexpected exception was encountered");


    }


}
