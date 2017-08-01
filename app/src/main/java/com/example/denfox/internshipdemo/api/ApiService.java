package com.example.denfox.internshipdemo.api;


import com.example.denfox.internshipdemo.models.GitRepoItem;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ApiService {

    @GET("/orgs/{username}/repos")
    void getUserRepos(@Path("username") String username, Callback<List<GitRepoItem>> callback);


}
