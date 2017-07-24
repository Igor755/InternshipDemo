package com.example.denfox.internshipdemo.utils;


import android.support.annotation.NonNull;

import com.example.denfox.internshipdemo.listeners.OnTaskItemLoadingCallback;
import com.example.denfox.internshipdemo.models.TaskItem;

public class HardTasks {

    public final static int THREAD_SLEEP_TIME = 5000;

    public void getTaskItemHArdly(@NonNull String taskName, OnTaskItemLoadingCallback callback) {
        synchronized (this) {
            if (callback != null) {
                callback.onLoadingStarted();
            }

            try {
                Thread.sleep(THREAD_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }

            if (callback != null) {
                callback.onLoadingFinish(new TaskItem(true, taskName, TaskItem.Type.PLACE, "13:00", "15/05/2017"));
            }
        }


    }

}
