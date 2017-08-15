package com.example.denfox.internshipdemo.mvp.mainscreen.contract;

import android.database.Cursor;
import android.net.Uri;

import com.example.denfox.internshipdemo.mvp.BaseView;


public interface MainContract {

    interface View extends BaseView<Presenter> {

        void openRepo(Uri uri);

        void showProgressBar();

        void hideProgressBar();

        void makeErrorToast(String errorMessage);

        void swapMainListCursor(Cursor cursor);

    }

    interface Presenter {

        void loadRepos(String username);

        void prepareData();

        boolean isUserRepoEnabled();

        boolean isDontClearEnabled();

        void setUserRepoEnabled(boolean isEnabled);

        void setDontClearEnabled(boolean isEnabled);

    }

}
