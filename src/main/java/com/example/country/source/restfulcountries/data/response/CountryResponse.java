package com.example.country.source.restfulcountries.data.response;

import lombok.Data;

@Data
public class CountryResponse {
    private String name;
    private String iso2;
    private String capital;
    private String population;
    private HrefResponse href;
}
