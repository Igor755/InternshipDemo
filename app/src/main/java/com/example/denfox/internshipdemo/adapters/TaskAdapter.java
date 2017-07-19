package com.example.denfox.internshipdemo.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.denfox.internshipdemo.R;
import com.example.denfox.internshipdemo.models.TaskItem;

import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {

    private ArrayList<TaskItem> items;
    private Context ctx;

    public TaskAdapter(ArrayList<TaskItem> items, Context ctx) {
        this.items = items;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public TaskItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(ctx);

        view = inflater.inflate(R.layout.main_list_item, viewGroup, false);

        CheckBox isCompleted = view.findViewById(R.id.item_task_check);
        TextView taskTitle = view.findViewById(R.id.item_task_title);
        ImageView taskTypeImage = view.findViewById(R.id.item_task_type_icon);
        TextView taskTime = view.findViewById(R.id.item_task_alarm_time);
        TextView taskDate = view.findViewById(R.id.item_task_alarm_date);

        isCompleted.setChecked(getItem(position).isCompleted());
        taskTitle.setText(getItem(position).getTaskName());
        taskDate.setText(getItem(position).getTaskDate());
        taskTime.setText(getItem(position).getTaskTime());

        switch (getItem(position).getTaskType()) {
            case ALARM:
                taskTypeImage.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_alarm_grey600_24dp));
                break;
            case NOTE:
                taskTypeImage.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_note_outline_grey600_24dp));
                break;
            case PLACE:
                taskTypeImage.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_map_marker_radius_grey600_24dp));
                break;
        }

        return view;
    }

    public ArrayList<TaskItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<TaskItem> items) {
        this.items = items;
    }
}
