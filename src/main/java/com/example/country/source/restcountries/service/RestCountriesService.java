package com.example.country.source.restcountries.service;

import com.example.country.data.CountryDto;
import com.example.country.data.CountryLiteDto;
import com.example.country.source.restcountries.data.mapper.CountryLiteResponseMapper;
import com.example.country.source.restcountries.data.mapper.CountryResponseMapper;
import com.example.country.source.restcountries.data.request.GetAllCountriesBasicInfoRequest;
import com.example.country.source.restcountries.data.request.GetCountryByNameRequest;
import com.example.country.source.restcountries.data.response.CountryLiteResponse;
import com.example.country.source.restcountries.data.response.CountryResponse;
import com.example.country.source.external.WebClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RestCountriesService {
    @Value("${restcountries.api.base.url}")
    private String restCountriesBaseUrl;

    @Autowired
    private WebClientService webClientService;

    public Flux<CountryLiteDto> getAllCountriesBasicInfo() {
        GetAllCountriesBasicInfoRequest request = new GetAllCountriesBasicInfoRequest(restCountriesBaseUrl);

        log.info(String.format("[RestCountries-ALL] Requesting all countries data, url: %s", request.getCompleteUrl()));
        return webClientService.fetchMultiData(request, CountryLiteResponse.class)
                .map(CountryLiteResponseMapper::toCountryLiteDto);
    }

    public Mono<CountryDto> getCountryByName(String name) {
        GetCountryByNameRequest request = new GetCountryByNameRequest(restCountriesBaseUrl, name);

        log.info(String.format("[RestCountries-ByName] Requesting country data of %s, url: %s", name, request.getCompleteUrl()));
        return  webClientService.fetchData(request, CountryResponse.class)
                .map(CountryResponseMapper::toCountryDto);
    }
}
