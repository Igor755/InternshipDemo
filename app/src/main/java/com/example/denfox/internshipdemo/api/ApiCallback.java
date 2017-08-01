package com.example.denfox.internshipdemo.api;


import com.example.denfox.internshipdemo.models.GitRepoErrorItem;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class ApiCallback<T> implements Callback<T> {

    public abstract void failure(GitRepoErrorItem gitRepoError);

    @Override
    public void failure(RetrofitError error) {

        GitRepoErrorItem repoError = null;

        try {
            repoError = (GitRepoErrorItem) error.getBodyAs(GitRepoErrorItem.class);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        if (repoError != null) {
            failure(repoError);
        } else {
            Response errorResponse = error.getResponse();
            if (error.getKind() == RetrofitError.Kind.NETWORK) {
                failure(new GitRepoErrorItem("Cant connect to network", "http://internet.com"));
            } else {
                failure(new GitRepoErrorItem("Error code: " + String.valueOf(errorResponse.getStatus()), "http://internet.com"));
            }
        }
    }
}
