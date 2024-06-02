package com.example.country.source.restcountries;

import com.example.country.source.CountryApi;
import com.example.country.data.CountryDto;
import com.example.country.data.CountryLiteDto;
import com.example.country.source.restcountries.service.RestCountriesService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class RestCountriesApi implements CountryApi {

    @Getter
    private final String code = "REST_COUNTRIES";

    @Autowired
    RestCountriesService restCountriesService;

    @Override
    public Flux<CountryLiteDto> getAllCountriesBasicInfo() {
        return restCountriesService.getAllCountriesBasicInfo();
    }

    @Override
    public Mono<CountryDto> getCountryByName(String countryName) {
        return restCountriesService.getCountryByName(countryName);
    }
}
