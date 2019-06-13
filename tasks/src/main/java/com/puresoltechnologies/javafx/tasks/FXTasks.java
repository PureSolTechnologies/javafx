package com.puresoltechnologies.javafx.tasks;

import java.util.ArrayList;
import java.util.List;

import com.puresoltechnologies.javafx.reactive.FluxStore;
import com.puresoltechnologies.javafx.reactive.ReactiveFX;
import com.puresoltechnologies.javafx.utils.FXThreads;

import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.scene.image.Image;

public class FXTasks {

    private static final List<Task<?>> runningTasks = new ArrayList<>();

    public static <T> Task<T> run(Task<T> task) {
	TaskInfo taskInfo = new TaskInfo(task);
	return run(task, taskInfo);
    }

    public static <T> Task<T> run(Task<T> task, Image image) {
	TaskInfo taskInfo = new TaskInfo(task, image);
	return run(task, taskInfo);
    }

    private static <T> Task<T> run(Task<T> task, TaskInfo taskInfo) {
	FluxStore fluxStore = ReactiveFX.getStore();
	fluxStore.publish(TasksTopics.TASK_STATUS_UPDATE, taskInfo);
	task.stateProperty().addListener((observable, oldValue, newValue) -> {
	    fluxStore.publish(TasksTopics.TASK_STATUS_UPDATE, taskInfo);
	});
	task.progressProperty().addListener((observable, oldValue, newValue) -> {
	    fluxStore.publish(TasksTopics.TASK_STATUS_UPDATE, taskInfo);
	});
	registerNewTask(task);
	FXThreads.runAsync(task);
	return task;
    }

    private static void registerNewTask(Task<?> task) {
	synchronized (runningTasks) {
	    runningTasks.add(task);
	    ReactiveFX.getStore().publish(TasksTopics.TASKS_SUMMARY, new TasksSummery(runningTasks.size(), 0.5));
	    task.progressProperty().addListener((observalble, oldValue, newValue) -> {
		sendSummaryUpdate();
	    });
	    task.stateProperty().addListener((observalble, oldValue, newValue) -> {
		if ((newValue == State.SUCCEEDED) || (newValue == State.FAILED) || (newValue == State.CANCELLED)) {
		    runningTasks.remove(task);
		}
		sendSummaryUpdate();
	    });
	}
    }

    private static void sendSummaryUpdate() {
	double totalProgress = 0.0;
	for (Task<?> task : runningTasks) {
	    totalProgress += task.getProgress();
	}
	totalProgress /= runningTasks.size() > 0 ? runningTasks.size() : 1.0;
	ReactiveFX.getStore().publish(TasksTopics.TASKS_SUMMARY, new TasksSummery(runningTasks.size(), totalProgress));
    }
}
