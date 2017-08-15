package com.example.denfox.internshipdemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.example.denfox.internshipdemo.mvp.mainscreen.presenter.MainPresenter;
import com.example.denfox.internshipdemo.mvp.mainscreen.view.MainFragment;
import com.example.denfox.internshipdemo.utils.Consts;
import com.example.denfox.internshipdemo.utils.Database;

public class NetworkDemoActivity extends AppCompatActivity {

    private RelativeLayout container;
    private FragmentManager fragmentManager;
    private Database database;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_demo);

        database = new Database(this);
        database.open();

        preferences = getSharedPreferences(Consts.PREFS_NAME, MODE_PRIVATE);

        MainFragment mainFragment = new MainFragment();
        new MainPresenter(mainFragment, database, preferences);

        container = (RelativeLayout) findViewById(R.id.fragment_container);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_container, mainFragment, "main_screen").commit();


    }
}
