package com.example.denfox.internshipdemo;

import android.app.LoaderManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.denfox.internshipdemo.adapters.GitRepoRecyclerAdapter;
import com.example.denfox.internshipdemo.api.ApiCallback;
import com.example.denfox.internshipdemo.api.RestClient;
import com.example.denfox.internshipdemo.listeners.OnGitRepoRecyclerItemClickListener;
import com.example.denfox.internshipdemo.models.GitRepoErrorItem;
import com.example.denfox.internshipdemo.models.GitRepoItem;
import com.example.denfox.internshipdemo.utils.Consts;
import com.example.denfox.internshipdemo.utils.Database;

import java.util.List;

import retrofit.client.Response;

public class NetworkDemoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView recycler;
    private EditText usernameInput;
    private Button goButton;
    private ProgressBar progressBar;
    private CheckBox showUserRepos;
    private CheckBox dontCleadList;

    private Database database;

    private GitRepoRecyclerAdapter adapter;

    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_demo);

        database = new Database(this);
        database.open();
        database.clearData();
        getLoaderManager().initLoader(Consts.LOADER_ID, null, this);

        preferences = getSharedPreferences(Consts.PREFS_NAME, MODE_PRIVATE);


        recycler = (RecyclerView) findViewById(R.id.rv_repos_recycler);
        usernameInput = (EditText) findViewById(R.id.et_username_input);
        goButton = (Button) findViewById(R.id.btn_go);
        progressBar = (ProgressBar) findViewById(R.id.pb_progress);
        showUserRepos = (CheckBox) findViewById(R.id.cbx_user_repo);
        dontCleadList = (CheckBox) findViewById(R.id.cbx_dont_clear);

        adapter = new GitRepoRecyclerAdapter(database.getAllData(), this, new OnGitRepoRecyclerItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Uri uri) {
                openRepo(uri);
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

        initCheckBox();

    }

    private void initCheckBox() {

        showUserRepos.setChecked(preferences.getBoolean(Consts.PREFS_USERS_REPO, false));
        dontCleadList.setChecked(preferences.getBoolean(Consts.PREFS_DONT_CLEAR_LIST, false));


        showUserRepos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                preferences.edit().putBoolean(Consts.PREFS_USERS_REPO, isChecked).apply(); //TODO: or commit()?
            }
        });

        dontCleadList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                preferences.edit().putBoolean(Consts.PREFS_DONT_CLEAR_LIST, isChecked).apply();
            }
        });

    }


    private void openRepo(Uri uri) {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, uri);
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

        ApiCallback<List<GitRepoItem>> callback = new ApiCallback<List<GitRepoItem>>() {
            @Override
            public void success(List<GitRepoItem> gitRepoItems, Response response) {

                if (!preferences.getBoolean(Consts.PREFS_DONT_CLEAR_LIST, false)) {
                    database.clearData();
                }
                database.addApiData(gitRepoItems);
                getLoaderManager().getLoader(Consts.LOADER_ID).forceLoad();
                hideProgressBlock();
            }

            @Override
            public void failure(GitRepoErrorItem error) {
                makeErrorToast(error.getMessage() + "\n" + "Details:" + error.getDocumentation_url());
                hideProgressBlock();
            }
        };

        if (preferences.getBoolean(Consts.PREFS_USERS_REPO, false)) {
            RestClient.getInstance().getService().getUserRepos(username, callback);
        } else {
            RestClient.getInstance().getService().getCompanyRepos(username, callback);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLoaderManager().destroyLoader(Consts.LOADER_ID);
        database.close();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(NetworkDemoActivity.this, database);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**
     * Subclass of {@link android.content.CursorLoader} which provides loader associated
     * with application database's implementation.
     */
    static class MyCursorLoader extends CursorLoader {

        Database db;

        public MyCursorLoader(Context context, Database db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            return db.getAllData();
        }

    }

}
