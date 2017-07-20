package com.example.denfox.internshipdemo;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.denfox.internshipdemo.adapters.TaskRecyclerAdapter;
import com.example.denfox.internshipdemo.listeners.OnTaskRecyclerItemClickListener;
import com.example.denfox.internshipdemo.models.TaskItem;

import java.util.ArrayList;

public class RecyclerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<TaskItem> items;
    TaskRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        recyclerView = (RecyclerView) findViewById(R.id.rv_recycler);

        items = new ArrayList<>();

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




    }
}
