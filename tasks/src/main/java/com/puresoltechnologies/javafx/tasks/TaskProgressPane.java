package com.puresoltechnologies.javafx.tasks;

import java.io.IOException;

import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class TaskProgressPane extends GridPane {

    private final String title;
    private final Image image;
    private final ProgressBar progressBar;
    private final Button abortButton;
    private DoubleProperty progress = new SimpleDoubleProperty(0.0);
    private StringProperty message = new SimpleStringProperty();

    public TaskProgressPane(String name) {
	this(name, null);
    }

    public TaskProgressPane(String title, Image image) {
	super();
	try {
	    this.title = title;
	    this.image = image;

	    Label titleLabel = new Label(title);
	    ImageView imageView = new ImageView(image);
	    progressBar = new ProgressBar(0.0);
	    progressBar.setMaxWidth(Double.MAX_VALUE);
	    progressBar.progressProperty().bind(progress);
	    ImageView crossView = ResourceUtils.getImageView(this, "/icons/FatCow_Icons16x16/cross.png");
	    abortButton = new Button("Abort...", crossView);
	    Label messageLabel = new Label();
	    messageLabel.textProperty().bind(message);

	    setConstraints(titleLabel, 1, 0, 2, 1, HPos.CENTER, VPos.CENTER, Priority.SOMETIMES, Priority.NEVER);
	    setConstraints(imageView, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.NEVER);
	    setConstraints(progressBar, 1, 1, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.ALWAYS, Priority.NEVER);
	    setConstraints(abortButton, 2, 1, 1, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.NEVER);
	    setConstraints(messageLabel, 1, 2, 2, 1, HPos.CENTER, VPos.CENTER, Priority.SOMETIMES, Priority.NEVER);
	    if (image != null) {
		getChildren().add(imageView);
	    }
	    getChildren().addAll(titleLabel, progressBar, abortButton, messageLabel);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public DoubleProperty progressProperty() {
	return progress;
    }

    public void setProgress(double progress) {
	this.progress.set(progress);
    }

    public double getProgress() {
	return progress.get();
    }

    public StringProperty messageProperty() {
	return message;
    }

    public void setMessage(String message) {
	this.message.set(message);
    }

    public String getMessage() {
	return this.message.get();
    }
}
