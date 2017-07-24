package com.example.denfox.internshipdemo.utils;


import com.example.denfox.internshipdemo.models.TaskItem;

import java.util.ArrayList;
import java.util.Random;

public class TaskLoader {

    private ArrayList<TaskItem> tasks;

    public TaskLoader() {
        tasks = new ArrayList<>();

        tasks.add(new TaskItem(true, "Создать этот список", TaskItem.Type.ALARM, "10:00", "07/05/2017"));
        tasks.add(new TaskItem(true, "Отметить первый пункт как готовый", TaskItem.Type.ALARM, "11:25", "07/05/2017"));
        tasks.add(new TaskItem(true, "Осознать что 2 пункта уже готовы", TaskItem.Type.NOTE, "11:30", "07/05/2017"));
        tasks.add(new TaskItem(false, "Вздремнуть", TaskItem.Type.PLACE, "11:35", "07/05/2017"));
        tasks.add(new TaskItem(false, "Заказать воду", TaskItem.Type.ALARM, "16:00", "09/05/2017"));
        tasks.add(new TaskItem(true, "Заплатить за интернет", TaskItem.Type.NOTE, "10:00", "11/05/2017"));
        tasks.add(new TaskItem(true, "Придумать следующий элемент списка", TaskItem.Type.NOTE, "12:00", "11/05/2017"));
        tasks.add(new TaskItem(false, "Сходить на работу", TaskItem.Type.PLACE, "09:00", "12/05/2017"));
        tasks.add(new TaskItem(false, "Провести занятие интернатуры", TaskItem.Type.PLACE, "13:00", "12/05/2017"));
        tasks.add(new TaskItem(false, "Развидеть увиденное", TaskItem.Type.ALARM, "00:00", "13/05/2017"));
        tasks.add(new TaskItem(false, "Перевернуть пингвина", TaskItem.Type.NOTE, "22:00", "13/05/2017"));
        tasks.add(new TaskItem(true, "Залипнуть в инете", TaskItem.Type.NOTE, "12:00", "14/05/2017"));
        tasks.add(new TaskItem(true, "Впихнуть невпихуемое", TaskItem.Type.PLACE, "13:00", "15/05/2017"));

    }

    public int getTaskCount() {
        return tasks.size();
    }

    public TaskItem loadTaskItemByIndex(int index) {
        try {
            Thread.sleep(new Random().nextInt(6500));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return tasks.get(index);
    }

}
