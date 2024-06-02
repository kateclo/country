package com.example.country.controller;

import com.example.country.exception.DataFetchingException;
import com.example.country.exception.UnsupportedSourceException;
import com.example.country.service.CountryService;
import com.example.country.data.CountryDto;
import com.example.country.data.CountryLiteDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/example/countries")
@RestController
public class CountryController {

    @Autowired
    CountryService countryService;

    @GetMapping
    public Flux<CountryLiteDto> getAllCountries() throws UnsupportedSourceException {
        return countryService.getAllCountries()
                .onErrorResume(error -> Mono.error(new DataFetchingException(error.getMessage())));

    }

    @GetMapping("/{name}")
    public Mono<CountryDto> getCountryByName(@PathVariable String name) throws UnsupportedSourceException {
        return countryService.getCountryByName(name)
                .onErrorResume(error -> Mono.error(new DataFetchingException(error.getMessage())));

    }
}
