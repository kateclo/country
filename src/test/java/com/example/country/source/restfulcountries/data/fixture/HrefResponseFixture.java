package com.example.country.source.restfulcountries.data.fixture;

import com.example.country.source.restfulcountries.data.response.HrefResponse;

public class HrefResponseFixture {
    public static HrefResponse hrefResponse() {
        HrefResponse hrefResponse = new HrefResponse();
        hrefResponse.setFlag("http://restful/some/flag/url");
        return hrefResponse;
    }
}
