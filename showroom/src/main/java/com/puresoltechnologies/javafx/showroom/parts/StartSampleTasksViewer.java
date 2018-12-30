package com.puresoltechnologies.javafx.showroom.parts;

import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.parts.AbstractViewer;
import com.puresoltechnologies.javafx.perspectives.parts.PartOpenMode;
import com.puresoltechnologies.javafx.tasks.FXTasks;

import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class StartSampleTasksViewer extends AbstractViewer {

    private final GridPane gridPane = new GridPane();
    private final TextField nameField = new TextField();
    private final Spinner<Integer> timeSpinner = new Spinner<>(1, 60, 5);
    private final Button startButton = new Button("Start Sample Task...");

    public StartSampleTasksViewer() {
	super("Create Sample Tasks", PartOpenMode.AUTO_AND_MANUAL);
    }

    @Override
    public Optional<PartHeaderToolBar> getToolBar() {
	return Optional.empty();
    }

    @Override
    public void initialize() {
	Label nameLabel = new Label("Name:");
	GridPane.setConstraints(nameLabel, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.NEVER);
	GridPane.setConstraints(nameField, 1, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);

	Label timeLabel = new Label("Time:");
	timeSpinner.setEditable(true);
	GridPane.setConstraints(timeLabel, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.NEVER);
	GridPane.setConstraints(timeSpinner, 1, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);

	GridPane.setConstraints(startButton, 0, 2, 2, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);

	gridPane.getChildren().addAll(nameLabel, nameField, timeLabel, timeSpinner, startButton);

	startButton.setOnAction(event -> {
	    startSampleTask();
	});
    }

    private void startSampleTask() {
	String title = nameField.getText();
	int time = timeSpinner.getValue();
	Task<Void> task = new Task<>() {

	    {
		updateTitle(title);
	    }

	    @Override
	    protected Void call() throws Exception {
		updateTitle(title);
		updateProgress(0, time);
		for (int i = 1; i < time; i++) {
		    Thread.sleep(1000);
		    updateProgress(i, time);
		}
		return null;
	    }
	};
	FXTasks.run(task);
    }

    @Override
    public void close() {
	// intentionally left blank
    }

    @Override
    public Node getContent() {
	return gridPane;
    }

}
