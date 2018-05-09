package com.puresoltechnologies.javafx.tasks;

import java.io.IOException;

import com.puresoltechnologies.javafx.utils.FXDefaultFonts;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class TaskProgressPane<T> extends GridPane {

    private final Task<T> task;
    private final Image image;
    private final ProgressBar progressBar;
    private final Button abortButton;

    public TaskProgressPane(Task<T> task) {
	this(null, task);
    }

    public TaskProgressPane(Image image, Task<T> task) {
	super();
	try {
	    this.image = image;
	    this.task = task;

	    Label titleLabel = new Label();
	    titleLabel.setFont(FXDefaultFonts.boldFont);
	    titleLabel.textProperty().bind(task.titleProperty());

	    ImageView imageView = new ImageView(image);
	    progressBar = new ProgressBar(0.0);
	    progressBar.setMaxWidth(Double.MAX_VALUE);
	    progressBar.progressProperty().bind(task.progressProperty());
	    ImageView crossView = ResourceUtils.getImageView(this, "/icons/FatCow_Icons16x16/cross.png");
	    abortButton = new Button("Abort...", crossView);
	    abortButton.setOnAction(event -> {
		task.cancel();
		event.consume();
	    });
	    Label messageLabel = new Label();
	    messageLabel.textProperty().bind(task.messageProperty());

	    setConstraints(titleLabel, 1, 0, 2, 1, HPos.LEFT, VPos.BASELINE, Priority.SOMETIMES, Priority.NEVER);
	    setConstraints(imageView, 0, 1, 1, 3, HPos.LEFT, VPos.TOP, Priority.NEVER, Priority.NEVER);
	    setConstraints(progressBar, 1, 1, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.ALWAYS, Priority.NEVER);
	    setConstraints(abortButton, 2, 1, 1, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.NEVER);
	    setConstraints(messageLabel, 1, 2, 2, 1, HPos.LEFT, VPos.BASELINE, Priority.SOMETIMES, Priority.NEVER);
	    if (image != null) {
		getChildren().add(imageView);
	    }
	    getChildren().addAll(titleLabel, progressBar, abortButton, messageLabel);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }
}
