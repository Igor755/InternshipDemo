package com.example.denfox.internshipdemo.api;


import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestClient {

    private static RestClient sInstance = new RestClient();

    private ApiService service;
    private RestAdapter restAdapter;
    private Gson gson;

    private final static String API_URL = "https://api.github.com";

    private RestClient() {

        gson = new GsonBuilder()
                .registerTypeAdapter(Uri.class, new UriDeserializer())
                .create();

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .setConverter(new GsonConverter(gson))
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
