package com.puresoltechnologies.javafx.showroom.parts;

import java.util.Optional;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.parts.AbstractViewer;
import com.puresoltechnologies.javafx.perspectives.parts.PartOpenMode;
import com.puresoltechnologies.javafx.reactive.ReactiveFX;
import com.puresoltechnologies.javafx.tasks.TaskInfo;
import com.puresoltechnologies.javafx.tasks.TasksTopics;
import com.puresoltechnologies.javafx.utils.FXThreads;

import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

public class SampleTaskResultsViewer extends AbstractViewer implements Subscriber<TaskInfo> {

    private final GridPane gridPane = new GridPane();
    private final Text text = new Text();
    private final Button clearButton = new Button("Clear");
    private Subscription subscription;

    public SampleTaskResultsViewer() {
	super("Sample Task Results", PartOpenMode.AUTO_AND_MANUAL);
    }

    @Override
    public Optional<PartHeaderToolBar> getToolBar() {
	return Optional.empty();
    }

    @Override
    public void initialize() {
	clearButton.setOnAction(event -> text.setText(""));

	GridPane.setConstraints(text, 0, 0, 1, 1, HPos.LEFT, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);
	GridPane.setConstraints(clearButton, 0, 1, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.NEVER);
	gridPane.getChildren().addAll(text, clearButton);
	ReactiveFX.getStore().subscribe(TasksTopics.TASK_STATUS_UPDATE, this);
    }

    @Override
    public void close() {
	if (subscription != null) {
	    subscription.cancel();
	}
    }

    @Override
    public Node getContent() {
	return gridPane;
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
    public void onNext(TaskInfo taskInfo) {
	Task<?> task = taskInfo.getTask();
	FXThreads.runOnFXThread(() -> {
	    State state = task.getState();
	    if ((state == State.SUCCEEDED) || (state == State.FAILED) || (state == State.CANCELLED)) {
		String report = text.getText();
		report += "'" + task.getTitle() + "' finished with: " + state.name() + ".\n";
		text.setText(report);
	    }
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
