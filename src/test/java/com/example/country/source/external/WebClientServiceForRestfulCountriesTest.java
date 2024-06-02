package com.example.country.source.external;

import com.example.country.source.restfulcountries.data.fixture.CountryLiteResponseFixture;
import com.example.country.source.restfulcountries.data.fixture.CountryResponseFixture;
import com.example.country.source.restfulcountries.data.request.GetAllCountriesRequest;
import com.example.country.source.restfulcountries.data.request.GetCountryByNameRequest;
import com.example.country.source.restfulcountries.data.response.CountriesDataResponse;
import com.example.country.source.restfulcountries.data.response.CountryLiteResponse;
import com.example.country.source.restfulcountries.data.response.CountryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class WebClientServiceForRestfulCountriesTest {
    @InjectMocks
    WebClientService webClientService;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient.Builder mutatedWebClientBuilder;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;


    @Test
    public void shouldFetchDataForCountryData() {
        // Given
        CountryResponse countryResponse = CountryResponseFixture.countryResponse();

        String baseUrl = "http://localhost:8080/baseurl";
        String authToken = "some auth token";
        String countryName = countryResponse.getName();
        GetCountryByNameRequest request = new GetCountryByNameRequest(baseUrl, authToken, countryName);

        when(webClient.mutate()).thenReturn(webClientBuilder);
        when(webClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(mutatedWebClientBuilder);
        when(mutatedWebClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(mutatedWebClientBuilder);
        when(mutatedWebClientBuilder.build()).thenReturn(webClient);

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

        verify(webClient, times(1)).mutate();
        verify(webClientBuilder, times(1)).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        verify(mutatedWebClientBuilder, times(1)).defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer some auth token");
        verify(mutatedWebClientBuilder, times(1)).build();

        verify(webClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1)).uri(request.getCompleteUrl());
        verify(requestHeadersSpec, times(1)).retrieve();
        verify(responseSpec, times(1)).bodyToFlux(CountryResponse.class);

    }

    @Test
    public void shouldFetchDataForCountries() {
        // Given
        CountryLiteResponse countryLiteResponse1 = CountryLiteResponseFixture.countryLiteResponse("Restful Country 1", "Code-1");
        CountryLiteResponse countryLiteResponse2 = CountryLiteResponseFixture.countryLiteResponse("Restful Country 2", "Code-2");
        CountryLiteResponse countryLiteResponse3 = CountryLiteResponseFixture.countryLiteResponse("Restful Country 3", "Code-3");
        CountryLiteResponse countryLiteResponse4 = CountryLiteResponseFixture.countryLiteResponse("Restful Country 4", "Code-4");
        CountryLiteResponse countryLiteResponse5 = CountryLiteResponseFixture.countryLiteResponse("Restful Country 5", "Code-5");

        CountriesDataResponse countriesDataResponse = new CountriesDataResponse();
        countriesDataResponse.setData(List.of(countryLiteResponse1, countryLiteResponse2, countryLiteResponse3, countryLiteResponse4, countryLiteResponse5));

        String baseUrl = "http://localhost:8080/baseurl";
        String authToken = "some auth token";
        GetAllCountriesRequest request = new GetAllCountriesRequest(baseUrl, authToken);

        when(webClient.mutate()).thenReturn(webClientBuilder);
        when(webClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(mutatedWebClientBuilder);
        when(mutatedWebClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(mutatedWebClientBuilder);
        when(mutatedWebClientBuilder.build()).thenReturn(webClient);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(any(Class.class))).thenAnswer(invocation -> {
            Class<?> responseType = invocation.getArgument(0);
            return Flux.just(responseType.cast(countriesDataResponse));
        });

        Mono<CountriesDataResponse> monoResponse = webClientService.fetchData(request, CountriesDataResponse.class);


        // Expect
        StepVerifier.create(monoResponse)
                .expectNextCount(1)
                .verifyComplete();

        verify(webClient, times(1)).mutate();
        verify(webClientBuilder, times(1)).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        verify(mutatedWebClientBuilder, times(1)).defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer some auth token");
        verify(mutatedWebClientBuilder, times(1)).build();

        verify(webClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1)).uri(request.getCompleteUrl());
        verify(requestHeadersSpec, times(1)).retrieve();
        verify(responseSpec, times(1)).bodyToFlux(CountriesDataResponse.class);

    }



}
