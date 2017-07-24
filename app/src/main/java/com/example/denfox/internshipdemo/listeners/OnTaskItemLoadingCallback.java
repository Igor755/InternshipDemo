package com.example.denfox.internshipdemo.listeners;


import com.example.denfox.internshipdemo.models.TaskItem;

public interface OnTaskItemLoadingCallback {

    void onLoadingStarted();

    void onLoadingFinish(TaskItem taskItem);

}
