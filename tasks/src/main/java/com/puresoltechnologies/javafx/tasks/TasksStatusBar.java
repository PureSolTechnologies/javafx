package com.puresoltechnologies.javafx.tasks;

import java.io.IOException;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import com.puresoltechnologies.javafx.reactive.MessageBroker;
import com.puresoltechnologies.javafx.utils.FXThreads;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
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
    private final Button openButton;
    private Subscription subscription;

    public TasksStatusBar() {
	super();
	try {
	    taskNumlabel = new Label("0 tasks");
	    progressBar = new ProgressBar(0.0);
	    ImageView detailsView = ResourceUtils.getImageView(this,
		    "icons/FatCow_Icons16x16/application_view_list.png");
	    progressBar.visibleProperty().bind(progressBar.progressProperty().greaterThan(0.0));
	    openButton = new Button("Overview...", detailsView);
	    openButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

	    setConstraints(taskNumlabel, 0, 0, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.NEVER, Priority.NEVER);
	    setConstraints(progressBar, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
	    setConstraints(openButton, 2, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER);

	    getChildren().addAll(taskNumlabel, progressBar, openButton);

	    MessageBroker.getStore().subscribe(TasksTopics.TASKS_SUMMARY, this);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
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
	    taskNumlabel.setText(summary.getTaskNum() + " tasks");
	    progressBar.setProgress(summary.getProgress());
	    progressBar.setTooltip(new Tooltip(summary.getTaskNum() + " tasks"));
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
