package com.example.denfox.internshipdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.denfox.internshipdemo.adapters.TaskRecyclerAdapter;
import com.example.denfox.internshipdemo.listeners.OnTaskItemLoadingCallback;
import com.example.denfox.internshipdemo.listeners.OnTaskRecyclerItemClickListener;
import com.example.denfox.internshipdemo.models.TaskItem;
import com.example.denfox.internshipdemo.utils.HardTasks;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton addBtn;
    private FloatingActionButton addBtnSecond;
    private ArrayList<TaskItem> items;
    private TaskRecyclerAdapter adapter;
    private ProgressBar progressBar;

    private HardTasks tasks;
    private HardTasks anotherTasks;

    private ExecutorService service = Executors.newFixedThreadPool(2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        recyclerView = (RecyclerView) findViewById(R.id.rv_recycler);
        addBtn = (FloatingActionButton) findViewById(R.id.fab_add);
        addBtnSecond = (FloatingActionButton) findViewById(R.id.fab_add_note);
        progressBar = (ProgressBar) findViewById(R.id.pb_progress);

        items = new ArrayList<>();
        tasks = new HardTasks();
        anotherTasks = new HardTasks();

        items.add(new TaskItem(true, "Создать этот список", TaskItem.Type.ALARM, "10:00", "07/05/2017"));
        items.add(new TaskItem(true, "Отметить первый пункт как готовый", TaskItem.Type.ALARM, "11:25", "07/05/2017"));
        items.add(new TaskItem(true, "Осознать что 2 пункта уже готовы", TaskItem.Type.NOTE, "11:30", "07/05/2017"));
        items.add(new TaskItem(false, "Вздремнуть", TaskItem.Type.PLACE, "11:35", "07/05/2017"));
        items.add(new TaskItem(false, "Заказать воду", TaskItem.Type.ALARM, "16:00", "09/05/2017"));
        items.add(new TaskItem(true, "Заплатить за интернет", TaskItem.Type.NOTE, "10:00", "11/05/2017"));
        items.add(new TaskItem(true, "Придумать следующий элемент списка", TaskItem.Type.NOTE, "12:00", "11/05/2017"));
        items.add(new TaskItem(false, "Сходить на работу", TaskItem.Type.PLACE, "09:00", "12/05/2017"));
        items.add(new TaskItem(false, "Провести занятие интернатуры", TaskItem.Type.PLACE, "13:00", "12/05/2017"));
        items.add(new TaskItem(false, "Развидеть увиденное", TaskItem.Type.ALARM, "00:00", "13/05/2017"));
        items.add(new TaskItem(false, "Перевернуть пингвина", TaskItem.Type.NOTE, "22:00", "13/05/2017"));
        items.add(new TaskItem(true, "Залипнуть в инете", TaskItem.Type.NOTE, "12:00", "14/05/2017"));
        items.add(new TaskItem(true, "Впихнуть невпихуемое", TaskItem.Type.PLACE, "13:00", "15/05/2017"));

        adapter = new TaskRecyclerAdapter(items, this, new OnTaskRecyclerItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                new AlertDialog.Builder(RecyclerActivity.this)
                        .setTitle("Full task name")
                        .setMessage(adapter.getItems().get(position).getTaskName())
                        .setCancelable(true)
                        .create().show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.submit(new Runnable() {
                    @Override
                    public void run() {
                        tasks.getTaskItemHArdly("SomeTask", taskItemLoadingCallback);
                    }
                });


            }
        });

        addBtnSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.submit(new Runnable() {
                    @Override
                    public void run() {
                        anotherTasks.getTaskItemHArdly("SomeAnotherTask", anotherTaskItemLoadingCallback);
                    }
                });
            }
        });


    }

    private void addItem(TaskItem item) {
        if (items != null && adapter != null) {
            items.add(item);
            adapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(recyclerView.getBottom());

        }
    }

    private void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    private void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

    }

    private OnTaskItemLoadingCallback taskItemLoadingCallback = new OnTaskItemLoadingCallback() {
        @Override
        public void onLoadingStarted() {
            Log.e("TaskLoader", "Starting LoadingTask");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showProgressBar();
                }
            });

        }

        @Override
        public void onLoadingFinish(final TaskItem taskItem) {
            Log.e("TaskLoader", "Task loaded!");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addItem(taskItem);
                    hideProgressBar();
                }
            });

        }
    };

    private OnTaskItemLoadingCallback anotherTaskItemLoadingCallback = new OnTaskItemLoadingCallback() {
        @Override
        public void onLoadingStarted() {
            Log.e("TaskLoader", "ANOTHER Starting Loading Task");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showProgressBar();
                }
            });

        }

        @Override
        public void onLoadingFinish(final TaskItem taskItem) {
            Log.e("TaskLoader", "ANOTHER Task loaded!");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addItem(taskItem);
                    hideProgressBar();
                }
            });

        }
    };

}
