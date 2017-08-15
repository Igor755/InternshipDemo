package com.example.denfox.internshipdemo.mvp.mainscreen.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.denfox.internshipdemo.R;
import com.example.denfox.internshipdemo.adapters.GitRepoRecyclerAdapter;
import com.example.denfox.internshipdemo.listeners.OnGitRepoRecyclerItemClickListener;
import com.example.denfox.internshipdemo.mvp.mainscreen.contract.MainContract;


public class MainFragment extends Fragment implements MainContract.View {

    private RecyclerView recycler;
    private EditText usernameInput;
    private Button goButton;
    private ProgressBar progressBar;
    private CheckBox showUserRepos;
    private CheckBox dontCleadList;

    private GitRepoRecyclerAdapter adapter;

    private MainContract.Presenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        presenter.prepareData();

        recycler = view.findViewById(R.id.rv_repos_recycler);
        usernameInput = view.findViewById(R.id.et_username_input);
        goButton = view.findViewById(R.id.btn_go);
        progressBar = view.findViewById(R.id.pb_progress);
        showUserRepos = view.findViewById(R.id.cbx_user_repo);
        dontCleadList = view.findViewById(R.id.cbx_dont_clear);

        adapter = new GitRepoRecyclerAdapter(null, getContext(), new OnGitRepoRecyclerItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Uri uri) {
                openRepo(uri);
            }
        });

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(usernameInput.getText().toString())) {
                    usernameInput.requestFocus();
                } else {
                    presenter.loadRepos(usernameInput.getText().toString());
                }
            }
        });

        initCheckBox();

        return view;
    }

    private void initCheckBox() {

        showUserRepos.setChecked(presenter.isUserRepoEnabled());
        dontCleadList.setChecked(presenter.isDontClearEnabled());


        showUserRepos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                presenter.setUserRepoEnabled(isChecked); //TODO: or commit()?
            }
        });

        dontCleadList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                presenter.setDontClearEnabled(isChecked);
            }
        });

    }

    @Override
    public void openRepo(Uri uri) {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void makeErrorToast(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void swapMainListCursor(Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
