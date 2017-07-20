package com.example.denfox.internshipdemo.adapters;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.denfox.internshipdemo.R;
import com.example.denfox.internshipdemo.listeners.OnTaskRecyclerItemClickListener;
import com.example.denfox.internshipdemo.models.TaskItem;

import java.util.ArrayList;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder> {

    private ArrayList<TaskItem> items;
    private Context ctx;
    private OnTaskRecyclerItemClickListener listener;

    public TaskRecyclerAdapter(ArrayList<TaskItem> items, Context ctx) {
        this.items = items;
        this.ctx = ctx;
    }

    public TaskRecyclerAdapter(ArrayList<TaskItem> items, Context ctx, OnTaskRecyclerItemClickListener listener) {
        this.items = items;
        this.ctx = ctx;
        this.listener = listener;
    }

    @Override
    public TaskRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(view, viewHolder.getAdapterPosition());
                }
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TaskRecyclerAdapter.ViewHolder holder, int position) {
        holder.isCompleted.setChecked(items.get(position).isCompleted());
        holder.taskTitle.setText(items.get(position).getTaskName());
        holder.taskDate.setText(items.get(position).getTaskDate());
        holder.taskTime.setText(items.get(position).getTaskTime());

        switch (items.get(position).getTaskType()) {
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

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<TaskItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<TaskItem> items) {
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox isCompleted;
        TextView taskTitle;
        ImageView taskTypeImage;
        TextView taskTime;
        TextView taskDate;

        public ViewHolder(View itemView) {
            super(itemView);

            Log.e("TaskRecyclerAdapter", "finding views!");

            isCompleted = itemView.findViewById(R.id.item_task_check);
            taskTitle = itemView.findViewById(R.id.item_task_title);
            taskTypeImage = itemView.findViewById(R.id.item_task_type_icon);
            taskTime = itemView.findViewById(R.id.item_task_alarm_time);
            taskDate = itemView.findViewById(R.id.item_task_alarm_date);

        }
    }

}
