package com.example.country.source.restfulcountries.data.response;

import lombok.Data;

import java.util.List;

@Data
public class CountriesDataResponse {
    List<CountryLiteResponse> data;
}
