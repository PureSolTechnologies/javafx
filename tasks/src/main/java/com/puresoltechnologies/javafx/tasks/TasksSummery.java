package com.puresoltechnologies.javafx.tasks;

public class TasksSummery {

    private final int taskNum;
    private final double progress;

    public TasksSummery(int taskNum, double progress) {
	super();
	this.taskNum = taskNum;
	this.progress = progress;
    }

    public int getTaskNum() {
	return taskNum;
    }

    public double getProgress() {
	return progress;
    }

}
