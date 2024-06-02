package com.example.country.source.external;

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
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class WebClientServiceForRestCountriesTest {
    @InjectMocks
    WebClientService webClientService;

    @Mock
    WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Test
    public void shouldFetchData() throws IOException {
        // Given
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();

        String baseUrl = "http://localhost:8080/baseurl";
        String countryName = countryResponse.getName().getCommon();
        GetCountryByNameRequest request = new GetCountryByNameRequest(baseUrl, countryName);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.bodyToFlux(any(Class.class))).thenAnswer(invocation -> {
            Class<?> responseType = invocation.getArgument(0);
            return Flux.just(responseType.cast(countryResponse));
        });

        Mono<CountryResponse> monoResponse = webClientService.fetchData(request, CountryResponse.class);

        // Expect
        StepVerifier.create(monoResponse)
                .expectNextCount(1)
                .verifyComplete();

        verify(webClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1)).uri(request.getCompleteUrl());
        verify(requestHeadersSpec, times(1)).retrieve();
        verify(responseSpec, times(1)).bodyToFlux(CountryResponse.class);

    }

    @Test
    public void shouldFetchMultiData() {
        // Given
        CountryLiteResponse countryLiteResponse1 = CountryLiteResponseFixture.countryLiteResponse("Country 1", "Code-1");
        CountryLiteResponse countryLiteResponse2 = CountryLiteResponseFixture.countryLiteResponse("Country 2", "Code-2");
        CountryLiteResponse countryLiteResponse3 = CountryLiteResponseFixture.countryLiteResponse("Country 3", "Code-3");
        CountryLiteResponse countryLiteResponse4 = CountryLiteResponseFixture.countryLiteResponse("Country 4", "Code-4");
        CountryLiteResponse countryLiteResponse5 = CountryLiteResponseFixture.countryLiteResponse("Country 5", "Code-5");

        String baseUrl = "http://localhost:8080/baseurl";
        GetAllCountriesBasicInfoRequest request = new GetAllCountriesBasicInfoRequest(baseUrl);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(any(Class.class))).thenAnswer(invocation -> {
            Class<?> responseType = invocation.getArgument(0);
            return Flux.just(
                                responseType.cast(countryLiteResponse1),
                                responseType.cast(countryLiteResponse2),
                                responseType.cast(countryLiteResponse3),
                                responseType.cast(countryLiteResponse4),
                                responseType.cast(countryLiteResponse5));
        });

        Flux<CountryLiteResponse> fluxResponse = webClientService.fetchMultiData(request, CountryLiteResponse.class);

        // Expect
        StepVerifier.create(fluxResponse)
                .expectNextCount(5)
                .verifyComplete();

        verify(webClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1)).uri(request.getCompleteUrl());
        verify(requestHeadersSpec, times(1)).retrieve();
        verify(responseSpec, times(1)).bodyToFlux(CountryLiteResponse.class);

    }
}
