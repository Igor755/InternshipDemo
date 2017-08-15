package com.example.denfox.internshipdemo.mvp.mainscreen.presenter;

import android.content.SharedPreferences;

import com.example.denfox.internshipdemo.api.ApiCallback;
import com.example.denfox.internshipdemo.api.RestClient;
import com.example.denfox.internshipdemo.models.GitRepoErrorItem;
import com.example.denfox.internshipdemo.models.GitRepoItem;
import com.example.denfox.internshipdemo.mvp.mainscreen.contract.MainContract;
import com.example.denfox.internshipdemo.utils.Consts;
import com.example.denfox.internshipdemo.utils.Database;

import java.util.List;

import retrofit.client.Response;


public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private Database database;
    private SharedPreferences preferences;

    public MainPresenter(MainContract.View view, Database database, SharedPreferences preferences) {
        this.preferences = preferences;
        this.database = database;
        this.view = view;

        view.setPresenter(this);

    }


    @Override
    public void loadRepos(String username) {
        view.showProgressBar();

        ApiCallback<List<GitRepoItem>> callback = new ApiCallback<List<GitRepoItem>>() {
            @Override
            public void success(List<GitRepoItem> gitRepoItems, Response response) {

                if (!preferences.getBoolean(Consts.PREFS_DONT_CLEAR_LIST, false)) {
                    database.clearData();
                }
                database.addApiData(gitRepoItems);
                view.swapMainListCursor(database.getAllData());
                view.hideProgressBar();
            }

            @Override
            public void failure(GitRepoErrorItem error) {
                view.makeErrorToast(error.getMessage() + "\n" + "Details:" + error.getDocumentation_url());
                view.hideProgressBar();
            }
        };

        if (preferences.getBoolean(Consts.PREFS_USERS_REPO, false)) {
            RestClient.getInstance().getService().getUserRepos(username, callback);
        } else {
            RestClient.getInstance().getService().getCompanyRepos(username, callback);
        }
    }

    @Override
    public void prepareData() {
        database.clearData();
    }

    @Override
    public boolean isUserRepoEnabled() {
        return preferences.getBoolean(Consts.PREFS_USERS_REPO, false);
    }

    @Override
    public boolean isDontClearEnabled() {
        return preferences.getBoolean(Consts.PREFS_DONT_CLEAR_LIST, false);
    }

    @Override
    public void setUserRepoEnabled(boolean isEnabled) {
        preferences.edit().putBoolean(Consts.PREFS_USERS_REPO, isEnabled).apply();

    }

    @Override
    public void setDontClearEnabled(boolean isEnabled) {
        preferences.edit().putBoolean(Consts.PREFS_DONT_CLEAR_LIST, isEnabled).apply();
    }
}
