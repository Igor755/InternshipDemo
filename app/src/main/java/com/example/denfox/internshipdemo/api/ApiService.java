package com.example.denfox.internshipdemo.api;


import com.example.denfox.internshipdemo.models.GitRepoItem;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

public interface ApiService {

    @GET("/orgs/{company_name}/repos")
    void getCompanyRepos(@Path("company_name") String companyName, ApiCallback<List<GitRepoItem>> callback);

    @GET("/users/{username}/repos")
    void getUserRepos(@Path("username") String username, ApiCallback<List<GitRepoItem>> callback);


}
