package com.example.country.source.restfulcountries.service;

import com.example.country.data.CountryDto;
import com.example.country.data.CountryLiteDto;
import com.example.country.source.restfulcountries.data.mapper.CountryLiteResponseMapper;
import com.example.country.source.restfulcountries.data.mapper.CountryResponseMapper;
import com.example.country.source.restfulcountries.data.request.GetAllCountriesRequest;
import com.example.country.source.restfulcountries.data.request.GetCountryByNameRequest;
import com.example.country.source.restfulcountries.data.response.CountriesDataResponse;
import com.example.country.source.restfulcountries.data.response.CountryByNameDataResponse;
import com.example.country.source.external.WebClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Service
public class RestfulCountriesService {

    @Value("${restfulcountries.api.base.url}")
    private String restfulCountriesBaseUrl;

    @Value("${restfulcountries.api.auth.token}")
    private String authToken;

    @Autowired
    private WebClientService webClientService;

    public Flux<CountryLiteDto> getAllCountriesBasicInfo() {
        GetAllCountriesRequest request = new GetAllCountriesRequest(restfulCountriesBaseUrl, authToken);

        log.info(String.format("[RestfulCountries-ALL] Requesting all countries data, url: %s", request.getCompleteUrl()));
        return webClientService.fetchData(request, CountriesDataResponse.class)
                .filter(data -> data != null && data.getData() != null)
                .flatMapMany(data -> Flux.fromIterable(data.getData()))
                .map(CountryLiteResponseMapper::toCountryLiteDto);
    }


    public Mono<CountryDto> getCountryByName(String name) {
        GetCountryByNameRequest request = new GetCountryByNameRequest(restfulCountriesBaseUrl, authToken, name);

        log.info(String.format("[RestfulCountries-ByName] Requesting country data of %s, url: %s", name, request.getCompleteUrl()));
        return  webClientService.fetchData(request, CountryByNameDataResponse.class)
                .filter(data -> data != null && data.getData() != null)
                .map(data -> CountryResponseMapper.toCountryDto(data.getData()));
    }
}
