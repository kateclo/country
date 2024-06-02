package com.example.country.source.restfulcountries;

import com.example.country.source.CountryApi;
import com.example.country.data.CountryDto;
import com.example.country.data.CountryLiteDto;
import com.example.country.source.restfulcountries.service.RestfulCountriesService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RestfulCountriesApi implements CountryApi {

    @Getter
    private final String code = "RESTFUL_COUNTRIES";

    @Autowired
    private RestfulCountriesService restfulCountriesService;

    @Override
    public Flux<CountryLiteDto> getAllCountriesBasicInfo() {
        return restfulCountriesService.getAllCountriesBasicInfo();
    }

    @Override
    public Mono<CountryDto> getCountryByName(String countryName) {
        return restfulCountriesService.getCountryByName(countryName);
    }
}
