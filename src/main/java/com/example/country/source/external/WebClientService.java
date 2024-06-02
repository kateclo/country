package com.example.country.source.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class WebClientService {

    @Value("${api.country.timeout.seconds:5}")
    private int timeoutConnInSeconds;
    @Autowired
    WebClient webClient;

    private void setAuthToken(SourceApiRequest request) {
        if (request.authToken() != null) {
            webClient = webClient.mutate()
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", request.authToken()))
                    .build();
        }

    }

    public <T> Mono<T> fetchData(SourceApiRequest request, Class<T> responseType) {
        setAuthToken(request);
        return webClient
                .get()
                .uri(request.getCompleteUrl())
                .retrieve()
                .bodyToFlux(responseType)
                .next()
                .timeout(Duration.ofSeconds(timeoutConnInSeconds));

    }

    public <T> Flux<T> fetchMultiData(SourceApiRequest request, Class<T> responseType) {
        setAuthToken(request);
        return webClient
                .get()
                .uri(request.getCompleteUrl())
                .retrieve()
                .bodyToFlux(responseType)
                .timeout(Duration.ofSeconds(timeoutConnInSeconds));
    }


}
