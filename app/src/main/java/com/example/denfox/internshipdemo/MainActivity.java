package com.example.denfox.internshipdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.denfox.internshipdemo.adapters.TaskAdapter;
import com.example.denfox.internshipdemo.models.TaskItem;
import com.example.denfox.internshipdemo.utils.TaskLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView tasksListView;
    private TaskAdapter adapter;
    private ArrayList<TaskItem> items;
    private TaskLoader loader;
    private LinearLayout loaderBlock;
    private ProgressBar progressBar;
    private TextView progressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksListView = (ListView) findViewById(R.id.list_view);
        loaderBlock = (LinearLayout) findViewById(R.id.rl_progress_block);
        progressBar = (ProgressBar) findViewById(R.id.pb_progress);
        progressText = (TextView) findViewById(R.id.tv_progress);

        items = new ArrayList<>();

        loader = new TaskLoader();


        adapter = new TaskAdapter(items, this);

        tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Full task name")
                        .setMessage(adapter.getItems().get(position).getTaskName())
                        .setCancelable(true)
                        .create().show();
            }
        });

        tasksListView.setAdapter(adapter);

        new TaskItemsApiTask().execute(loader.getTaskCount());


    }

    private void setLoadingProgress(int progress) {
        if (progressBar != null) {
            progressBar.setProgress(progress);
            progressText.setText(String.valueOf(progress) + "%");
        }
    }

    private void showProgressBlock() {
        if (loaderBlock != null) {
            loaderBlock.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBlock() {
        if (loaderBlock != null) {
            loaderBlock.setVisibility(View.GONE);
        }
    }


    public class TaskItemsApiTask extends AsyncTask<Integer, Integer, ArrayList<TaskItem>> {

        @Override
        protected ArrayList<TaskItem> doInBackground(Integer... integers) {
            int itemsCount = integers[0];
            for (int i = 0; i < itemsCount; i++) {
                items.add(loader.loadTaskItemByIndex(i));
                publishProgress(i, itemsCount);

            }
            return items;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            items.clear();
            setLoadingProgress(0);
            showProgressBlock();

        }

        @Override
        protected void onPostExecute(ArrayList<TaskItem> taskItems) {
            super.onPostExecute(taskItems);
            hideProgressBlock();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            adapter.notifyDataSetChanged();
            int completed = values[0];
            int total = values[1];

            int percentsCompleted = (int) (((float) completed / total) * 100);

            setLoadingProgress(percentsCompleted);
        }
    }

}
