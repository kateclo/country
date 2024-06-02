package com.example.country.source.restcountries.data.fixture;

import com.example.country.source.restcountries.data.response.FlagsResponse;

public class FlagsResponseFixture {
    public static FlagsResponse flagsResponse() {
        FlagsResponse flagsResponse = new FlagsResponse();
        flagsResponse.setPng("http://some.png.file.url");
        flagsResponse.setSvg("http://some.svg.file.url");
        return flagsResponse;
    }
}
