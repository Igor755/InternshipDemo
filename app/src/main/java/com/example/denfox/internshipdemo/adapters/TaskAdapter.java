package com.example.denfox.internshipdemo.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;
        View view = convertView;

        if (view == null) {
            Log.e("TaskAdapter", "inflatingView!");
            LayoutInflater inflater = LayoutInflater.from(ctx);
            view = inflater.inflate(R.layout.main_list_item, viewGroup, false);

            holder = new ViewHolder();

            holder.isCompleted = view.findViewById(R.id.item_task_check);
            holder.taskTitle = view.findViewById(R.id.item_task_title);
            holder.taskTypeImage = view.findViewById(R.id.item_task_type_icon);
            holder.taskTime = view.findViewById(R.id.item_task_alarm_time);
            holder.taskDate = view.findViewById(R.id.item_task_alarm_date);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        holder.isCompleted.setChecked(getItem(position).isCompleted());
        holder.taskTitle.setText(getItem(position).getTaskName());
        holder.taskDate.setText(getItem(position).getTaskDate());
        holder.taskTime.setText(getItem(position).getTaskTime());

        switch (getItem(position).getTaskType()) {
            case ALARM:
                holder.taskTypeImage.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_alarm_grey600_24dp));
                break;
            case NOTE:
                holder.taskTypeImage.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_note_outline_grey600_24dp));
                break;
            case PLACE:
                holder.taskTypeImage.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_map_marker_radius_grey600_24dp));
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


    private static class ViewHolder {

        CheckBox isCompleted;
        TextView taskTitle;
        ImageView taskTypeImage;
        TextView taskTime;
        TextView taskDate;
    }

}