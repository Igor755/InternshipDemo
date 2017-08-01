package com.example.denfox.internshipdemo.api;


import retrofit.RestAdapter;

public class RestClient {

    private static RestClient sInstance = new RestClient();

    private ApiService service;
    private RestAdapter restAdapter;

    private final static String API_URL = "https://api.github.com";

    private RestClient() {

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .build();

        service = restAdapter.create(ApiService.class);

    }

    public static RestClient getInstance() {
        return sInstance;
    }

    public ApiService getService() {
        return service;
    }

}
