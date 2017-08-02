package com.example.denfox.internshipdemo.adapters;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.denfox.internshipdemo.R;
import com.example.denfox.internshipdemo.listeners.OnGitRepoRecyclerItemClickListener;
import com.example.denfox.internshipdemo.models.GitRepoItem;
import com.example.denfox.internshipdemo.utils.Consts;

public class GitRepoRecyclerAdapter extends CursorRecyclerViewAdapter<GitRepoRecyclerAdapter.ViewHolder> {
    private Context ctx;
    private OnGitRepoRecyclerItemClickListener listener;

    public GitRepoRecyclerAdapter(Cursor cursor, Context ctx) {
        super(ctx, cursor);
        this.ctx = ctx;
    }

    public GitRepoRecyclerAdapter(Cursor cursor, Context ctx, OnGitRepoRecyclerItemClickListener listener) {
        super(ctx, cursor);
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
                    listener.onItemClick(view, viewHolder.getAdapterPosition(), getItem(viewHolder.getAdapterPosition()).getWebUrl());
                }
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GitRepoRecyclerAdapter.ViewHolder holder, Cursor cursor) {
        holder.description.setText(cursor.getString(cursor.getColumnIndex(Consts.DB_COL_DESCRIPTION)));
        holder.name.setText(cursor.getString(cursor.getColumnIndex(Consts.DB_COL_NAME)));

    }

    public GitRepoItem getItem(int position) {
        Cursor cursor = getCursor();
        GitRepoItem item = new GitRepoItem();

        if(cursor.moveToPosition(position)) {
            item.setDescription(cursor.getString(cursor.getColumnIndex(Consts.DB_COL_DESCRIPTION)));
            item.setRepoId(cursor.getInt(cursor.getColumnIndex(Consts.DB_COL_ID)));
            item.setRepoName(cursor.getString(cursor.getColumnIndex(Consts.DB_COL_NAME)));
            item.setWebUrl(Uri.parse(cursor.getString(cursor.getColumnIndex(Consts.DB_COL_URL))));
        }

        return item;

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
