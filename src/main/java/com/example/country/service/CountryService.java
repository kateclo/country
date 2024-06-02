package com.example.country.service;

import com.example.country.exception.UnsupportedSourceException;
import com.example.country.source.CountryApi;
import com.example.country.data.CountryDto;
import com.example.country.data.CountryLiteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CountryService {

    @Value("${api.country.source}")
    private String source;

    @Autowired
    private List<CountryApi> countryApiList;

    public Flux<CountryLiteDto> getAllCountries() throws UnsupportedSourceException {
        CountryApi countryApi = getCountryApi();
        return countryApi.getAllCountriesBasicInfo();
    }

    public Mono<CountryDto> getCountryByName(String name) throws UnsupportedSourceException {
        CountryApi countryApi = getCountryApi();
        return countryApi.getCountryByName(name);
    }

    private CountryApi getCountryApi() throws UnsupportedSourceException {
        return countryApiList.stream()
                .filter(api -> api.getCode() != null && api.getCode().equalsIgnoreCase(source))
                .findFirst()
                .orElseThrow(() -> new UnsupportedSourceException(source));
    }
}
