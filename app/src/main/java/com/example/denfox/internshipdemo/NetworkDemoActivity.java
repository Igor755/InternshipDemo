package com.example.denfox.internshipdemo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.denfox.internshipdemo.adapters.GitRepoRecyclerAdapter;
import com.example.denfox.internshipdemo.api.ApiCallback;
import com.example.denfox.internshipdemo.api.RestClient;
import com.example.denfox.internshipdemo.listeners.OnGitRepoRecyclerItemClickListener;
import com.example.denfox.internshipdemo.models.GitRepoErrorItem;
import com.example.denfox.internshipdemo.models.GitRepoItem;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Response;

public class NetworkDemoActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private EditText usernameInput;
    private Button goButton;
    private ProgressBar progressBar;

    private ArrayList<GitRepoItem> items;
    private GitRepoRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_demo);

        items = new ArrayList<>();


        recycler = (RecyclerView) findViewById(R.id.rv_repos_recycler);
        usernameInput = (EditText) findViewById(R.id.et_username_input);
        goButton = (Button) findViewById(R.id.btn_go);
        progressBar = (ProgressBar) findViewById(R.id.pb_progress);

        adapter = new GitRepoRecyclerAdapter(items, this, new OnGitRepoRecyclerItemClickListener() {
            @Override
            public void onItemClick(View v, int position, String url) {
                openRepo(url);
            }
        });

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(usernameInput.getText().toString())) {
                    usernameInput.requestFocus();
                } else {
                    loadRepos(usernameInput.getText().toString());
                }
            }
        });

    }


    private void openRepo(String url) {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void showProgressBlock() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    private void hideProgressBlock() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void makeErrorToast(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void loadRepos(String username) {
        showProgressBlock();
        RestClient.getInstance().getService().getUserRepos(username, new ApiCallback<List<GitRepoItem>>() {
            @Override
            public void success(List<GitRepoItem> gitRepoItems, Response response) {
                items.clear();
                items.addAll(gitRepoItems);
                adapter.notifyDataSetChanged();
                hideProgressBlock();
            }

            @Override
            public void failure(GitRepoErrorItem error) {
                makeErrorToast(error.getMessage() + "\n" + "Details:" + error.getDocumentation_url());
                hideProgressBlock();
            }
        });
    }

}
