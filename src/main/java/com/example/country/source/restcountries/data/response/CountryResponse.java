package com.example.country.source.restcountries.data.response;

import lombok.Data;

import java.util.List;

@Data
public class CountryResponse {
    private NameResponse name;
    private FlagsResponse flags;
    private List<String> capital;
    private String cca2;
    private long population;
}
