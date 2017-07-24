package com.example.denfox.internshipdemo;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.denfox.internshipdemo.adapters.TaskAdapter;
import com.example.denfox.internshipdemo.models.TaskItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView tasksListView;
    private TaskAdapter adapter;
    private ArrayList<TaskItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksListView = (ListView) findViewById(R.id.list_view);

        items = new ArrayList<>();

        //TODO: fulfill items!


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


    }
}
