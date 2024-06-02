package com.example.country.source.restcountries.data.fixture;

import com.example.country.source.restcountries.data.response.NameResponse;

public class NameResponseFixture {
    public static NameResponse nameResponse() {
        NameResponse nameResponse = new NameResponse();
        nameResponse.setCommon("Common Name");
        nameResponse.setOfficial("Official Name");
        return nameResponse;
    }

    public static NameResponse nameResponse(String commonName) {
        NameResponse nameResponse = new NameResponse();
        nameResponse.setCommon(commonName);
        nameResponse.setOfficial("Official " + commonName);
        return nameResponse;
    }
}
