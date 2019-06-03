package com.puresoltechnologies.javafx.showroom.parts;

import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.parts.AbstractViewer;
import com.puresoltechnologies.javafx.perspectives.parts.PartContentType;
import com.puresoltechnologies.javafx.perspectives.parts.PartOpenMode;
import com.puresoltechnologies.javafx.showroom.tasks.SampleTask;
import com.puresoltechnologies.javafx.showroom.tasks.SampleTaskType;
import com.puresoltechnologies.javafx.tasks.FXTasks;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;

public class StartSampleTasksViewer extends AbstractViewer {

    private final GridPane gridPane = new GridPane();
    private final ComboBox<SampleTaskType> typeChoiceBox = new ComboBox<>();
    private final TextField nameField = new TextField();
    private final Spinner<Integer> timeSpinner = new Spinner<>(1, 60, 5);
    private final Button startButton = new Button("Start Sample Task...");

    public StartSampleTasksViewer() {
	super("Create Sample Tasks", PartOpenMode.AUTO_AND_MANUAL, PartContentType.ONE_PER_PERSPECTIVE);
    }

    @Override
    public Optional<PartHeaderToolBar> getToolBar() {
	return Optional.empty();
    }

    @Override
    public void initialize() {
	Label typeLabel = new Label("Name:");
	typeChoiceBox.setItems(FXCollections.observableArrayList(SampleTaskType.values()));
	typeChoiceBox.setCellFactory((view) -> {
	    return new ListCell<>() {
		@Override
		protected void updateItem(SampleTaskType item, boolean empty) {
		    super.updateItem(item, empty);
		    if ((item == null) || empty) {
			setGraphic(null);
		    } else {
			setText(item.getDescription());
		    }
		}
	    };
	});
	typeChoiceBox.setConverter(new StringConverter<SampleTaskType>() {

	    @Override
	    public String toString(SampleTaskType object) {
		if (object == null) {
		    return "<no selection>";
		}
		return object.getDescription();
	    }

	    @Override
	    public SampleTaskType fromString(String string) {
		return SampleTaskType.byDescription(string);
	    }
	});
	typeChoiceBox.setValue(SampleTaskType.WITH_PROGRESS);
	GridPane.setConstraints(typeLabel, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.NEVER);
	GridPane.setConstraints(typeChoiceBox, 1, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);

	Label nameLabel = new Label("Name:");
	GridPane.setConstraints(nameLabel, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.NEVER);
	GridPane.setConstraints(nameField, 1, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);

	Label timeLabel = new Label("Time:");
	timeSpinner.setEditable(true);
	GridPane.setConstraints(timeLabel, 0, 2, 1, 1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.NEVER);
	GridPane.setConstraints(timeSpinner, 1, 2, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);

	GridPane.setConstraints(startButton, 0, 3, 2, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);

	gridPane.getChildren().addAll(typeLabel, typeChoiceBox, nameLabel, nameField, timeLabel, timeSpinner,
		startButton);

	startButton.setOnAction(event -> {
	    startSampleTask();
	});
    }

    private void startSampleTask() {
	SampleTaskType type = typeChoiceBox.getValue();
	String title = nameField.getText();
	int time = timeSpinner.getValue();
	SampleTask task = new SampleTask(type, title, time);
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
