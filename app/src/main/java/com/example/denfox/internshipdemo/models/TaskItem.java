package com.example.denfox.internshipdemo.models;


public class TaskItem {

    private boolean isCompleted;
    private String taskName;
    private Type taskType;
    private String taskTime;
    private String taskDate;

    public enum Type {NOTE, ALARM, PLACE}


    public TaskItem(boolean isCompleted, String taskName, Type taskType, String taskTime, String taskDate) {
        this.isCompleted = isCompleted;
        this.taskName = taskName;
        this.taskType = taskType;
        this.taskTime = taskTime;
        this.taskDate = taskDate;
    }

    public TaskItem(boolean isCompleted, String taskName, String taskTime, String taskDate) {
        this(isCompleted, taskName, Type.ALARM, taskTime, taskDate);
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Type getTaskType() {
        return taskType;
    }

    public void setTaskType(Type taskType) {
        this.taskType = taskType;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    @Override
    public int hashCode() {
        return taskName.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        TaskItem other = (TaskItem) o;
        if (!taskName.equals(other.getTaskName()))
            return false;
        return true;
    }

}
