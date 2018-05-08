package com.puresoltechnologies.javafx.tasks;

import java.util.Optional;

import javafx.concurrent.Task;
import javafx.scene.image.Image;

public class TaskInfo {

    private final Task<?> task;
    private final Optional<Image> image;
    private final Optional<String> message;

    public TaskInfo(Task<?> task) {
	this.task = task;
	this.message = Optional.empty();
	this.image = Optional.empty();
    }

    public TaskInfo(Task<?> task, Image image) {
	this.task = task;
	this.message = Optional.empty();
	this.image = Optional.of(image);
    }

    public TaskInfo(Task<?> task, String message) {
	this.task = task;
	this.message = Optional.of(message);
	this.image = Optional.empty();
    }

    public TaskInfo(Task<?> task, String message, Image image) {
	this.task = task;
	this.message = Optional.of(message);
	this.image = Optional.of(image);
    }

    public Task<?> getTask() {
	return task;
    }

    public Optional<String> getMessage() {
	return message;
    }

    public String getTitle() {
	return task.getTitle();
    }

    public Optional<Image> getImage() {
	return image;
    }

    public double getProgress() {
	return task.getProgress();
    }

}
