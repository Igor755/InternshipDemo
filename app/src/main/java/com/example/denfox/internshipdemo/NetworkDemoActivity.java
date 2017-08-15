package com.example.denfox.internshipdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.example.denfox.internshipdemo.mainscreen.MainFragment;

public class NetworkDemoActivity extends AppCompatActivity {

    private RelativeLayout container;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_demo);

        container = (RelativeLayout) findViewById(R.id.fragment_container);

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.fragment_container, new MainFragment(), "main_screen").commit();


    }
}
