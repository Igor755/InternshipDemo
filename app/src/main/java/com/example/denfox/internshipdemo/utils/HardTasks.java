package com.example.denfox.internshipdemo.utils;


import android.support.annotation.NonNull;

import com.example.denfox.internshipdemo.models.TaskItem;

public class HardTasks {

    public final static int THREAD_SLEEP_TIME = 5000;

    public static TaskItem getTaskItemHArdly(@NonNull String taskName) {

        try {
            Thread.sleep(THREAD_SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        return new TaskItem(true, taskName, TaskItem.Type.PLACE, "13:00", "15/05/2017");
    }

}
