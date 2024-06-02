package com.example.country.source;

import com.example.country.data.CountryDto;
import com.example.country.data.CountryLiteDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CountryApi {

    String getCode();

    Flux<CountryLiteDto> getAllCountriesBasicInfo();
    Mono<CountryDto> getCountryByName(String countryName);
}
