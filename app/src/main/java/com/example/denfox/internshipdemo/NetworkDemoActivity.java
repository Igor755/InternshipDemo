package com.example.denfox.internshipdemo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.example.denfox.internshipdemo.listeners.OnGitRepoRecyclerItemClickListener;
import com.example.denfox.internshipdemo.models.GitRepoItem;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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
                    new LoadReposTask().execute(usernameInput.getText().toString());
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
                    + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
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


    public class LoadReposTask extends AsyncTask<String, Void, String> {

        private HttpClient httpClient = new DefaultHttpClient();
        private final static String URL = "https://api.github.com/orgs/%s/repos";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            items.clear();
            showProgressBlock();
        }

        @Override
        protected void onPostExecute(String error) {
            super.onPostExecute(error);
            hideProgressBlock();
            if (error != null) {
                makeErrorToast(error);
            } else {
                adapter.notifyDataSetChanged();
            }

        }

        @Override
        protected String doInBackground(String... strings) {

            String repoName = strings[0];

            HttpGet request = new HttpGet(Uri.decode(String.format(URL, repoName)));
            HttpResponse response;
            try {
                response = httpClient.execute(request);
            } catch (IOException e) {
                e.printStackTrace();
                return "Unhandled server error";
            }

            String jsonString;
            try {
                jsonString = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
                return "Unhandled parsing error";
            }

            if (response.getStatusLine().getStatusCode() == 200) {

                try {
                    JSONArray array = new JSONArray(jsonString);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        items.add(new GitRepoItem(object));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "JSON parsing error";
                }

            } else {
                try {
                    JSONObject errorObject = new JSONObject(jsonString);
                    if (errorObject.has("message")) {
                        return errorObject.getString("message");
                    } else {
                        return "Request error with no message";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error parsing error";
                }
            }

            return null;
        }
    }

}
