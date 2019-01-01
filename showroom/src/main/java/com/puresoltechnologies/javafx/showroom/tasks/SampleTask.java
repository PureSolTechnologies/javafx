package com.puresoltechnologies.javafx.showroom.tasks;

import javafx.concurrent.Task;

/**
 * This is the sample task implementation for demonstration purposes.
 *
 * @author Rick-
 *
 */
public class SampleTask extends Task<Void> {

    private final SampleTaskType type;
    private final int time;

    /**
     *
     * @param type  is the {@link SampleTaskType} to demonstrate different behavior.
     * @param title the task title.
     * @param time  task run time in seconds.
     */
    public SampleTask(SampleTaskType type, String title, int time) {
	this.type = type;
	this.time = time;
	updateTitle(title);
    }

    @Override
    protected Void call() throws Exception {
	switch (type) {
	case WITH_PROGRESS:
	    updateProgress(0, time);
	    for (int i = 1; i < time; i++) {
		updateMessage("Elapsed: " + i + "s");
		Thread.sleep(1000);
		updateProgress(i, time);
	    }
	    break;
	case NO_FEEDBACK:
	    for (int i = 0; i < time; i++) {
		updateMessage("Elapsed: " + i + "s");
		Thread.sleep(1000);
	    }
	    break;
	default:
	    throw new IllegalStateException("Sample task type '" + type.name() + "' not supported.");
	}
	return null;
    }
}
