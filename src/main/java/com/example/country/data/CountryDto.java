package com.example.country.data;

import lombok.Data;

@Data
public class CountryDto {
    private String name;
    private String countryCode;
    private String capital;
    private String flagFileUrl;
    private long population;
}
