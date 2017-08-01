package com.example.denfox.internshipdemo.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.denfox.internshipdemo.R;
import com.example.denfox.internshipdemo.listeners.OnGitRepoRecyclerItemClickListener;
import com.example.denfox.internshipdemo.models.GitRepoItem;

import java.util.ArrayList;

public class GitRepoRecyclerAdapter extends RecyclerView.Adapter<GitRepoRecyclerAdapter.ViewHolder> {

    private ArrayList<GitRepoItem> items;
    private Context ctx;
    private OnGitRepoRecyclerItemClickListener listener;

    public GitRepoRecyclerAdapter(ArrayList<GitRepoItem> items, Context ctx) {
        this.items = items;
        this.ctx = ctx;
    }

    public GitRepoRecyclerAdapter(ArrayList<GitRepoItem> items, Context ctx, OnGitRepoRecyclerItemClickListener listener) {
        this.items = items;
        this.ctx = ctx;
        this.listener = listener;
    }

    @Override
    public GitRepoRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.git_repo_list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(view, viewHolder.getAdapterPosition(), items.get(viewHolder.getAdapterPosition()).getWebUrl());
                }
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GitRepoRecyclerAdapter.ViewHolder holder, int position) {
        holder.description.setText(items.get(position).getDescription());
        holder.name.setText(items.get(position).getRepoName());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<GitRepoItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<GitRepoItem> items) {
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            Log.e("TaskRecyclerAdapter", "finding views!");

            description = itemView.findViewById(R.id.tv_repo_desc);
            name = itemView.findViewById(R.id.tv_repo_name);

        }
    }

}
