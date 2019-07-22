package com.puresoltechnologies.javafx.tasks;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import com.puresoltechnologies.javafx.reactive.MessageBroker;
import com.puresoltechnologies.javafx.utils.FXThreads;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * This class is used for instance in status bars to show the overall status of
 * all running tasks.
 *
 * @author Rick-Rainer Ludwig
 */
public class TasksStatusBar extends GridPane implements AutoCloseable, Subscriber<TasksSummery> {

    private final Label taskNumlabel;
    private final ProgressBar progressBar;
    private Subscription subscription;
    private final IntegerProperty taskNumProperty = new SimpleIntegerProperty(0);

    public TasksStatusBar() {
	super();
	setPadding(new Insets(0.0, 5.0, 0.0, 5.0));
	taskNumlabel = new Label("");
	progressBar = new ProgressBar(0.0);
	progressBar.disableProperty().bind(taskNumProperty.isEqualTo(0));
	taskNumlabel.visibleProperty().bind(taskNumProperty.greaterThan(0));

	setConstraints(taskNumlabel, 0, 0, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.NEVER, Priority.NEVER);
	setConstraints(progressBar, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);

	getChildren().addAll(taskNumlabel, progressBar);

	MessageBroker.getBroker().subscribe(TasksTopics.TASKS_SUMMARY, this);
    }

    @Override
    public void close() {
	if (subscription != null) {
	    subscription.cancel();
	    subscription = null;
	}
    }

    @Override
    public void onSubscribe(Subscription subscription) {
	if (this.subscription != null) {
	    this.subscription.cancel();
	}
	this.subscription = subscription;
	subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(TasksSummery summary) {
	FXThreads.runOnFXThread(() -> {
	    int taskNum = summary.getTaskNum();
	    taskNumProperty.set(taskNum);
	    if (taskNum > 0) {
		taskNumlabel.setText(taskNum + " tasks running");
		progressBar
			.setTooltip(new Tooltip(taskNum + " tasks are currently being processed in the background."));
	    } else {
		taskNumlabel.setText("");
		progressBar.setTooltip(new Tooltip("Currently no tasks are running."));
	    }
	    progressBar.setProgress(summary.getProgress());
	});
    }

    @Override
    public void onError(Throwable throwable) {
	// TODO
    }

    @Override
    public void onComplete() {
	// Intentionally left blank
    }

}
