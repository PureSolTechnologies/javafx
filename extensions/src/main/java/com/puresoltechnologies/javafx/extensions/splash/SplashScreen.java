package com.puresoltechnologies.javafx.extensions.splash;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import com.puresoltechnologies.javafx.extensions.StatusBar;
import com.puresoltechnologies.javafx.utils.FXThreads;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SplashScreen {

    private final Stage initStage;
    private final Consumer<Stage> startStage;
    private final Scene scene;
    private final GridPane root = new GridPane();
    private final ProgressBar loadProgress = new ProgressBar();
    private final Label amountLabel = new Label();
    private final Label titleLabel = new Label();

    private final List<Task<?>> tasks = new ArrayList<>();

    public SplashScreen(Stage initStage, Image splashImage, Consumer<Stage> startStage) {
	super();
	this.initStage = initStage;
	this.startStage = startStage;
	try {
	    ImageView imageView = new ImageView(splashImage);
	    GridPane.setFillWidth(imageView, true);
	    GridPane.setFillHeight(imageView, true);

	    loadProgress.setMinHeight(16);
	    loadProgress.setPrefHeight(16);

	    root.getChildren().addAll(imageView, loadProgress, amountLabel, titleLabel);
	    GridPane.setConstraints(imageView, 0, 0, 2, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
	    GridPane.setConstraints(loadProgress, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
	    GridPane.setFillWidth(loadProgress, true);
	    GridPane.setFillHeight(loadProgress, true);
	    GridPane.setConstraints(amountLabel, 1, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES,
		    Priority.NEVER);
	    GridPane.setConstraints(titleLabel, 0, 2, 2, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);

	    Bounds cellBounds = root.getCellBounds(0, 1);
	    loadProgress.setPrefWidth(cellBounds.getWidth());

	    scene = new Scene(root, Color.TRANSPARENT);
	    initStage.setScene(scene);

	    final Rectangle2D bounds = Screen.getPrimary().getBounds();
	    initStage.setX((bounds.getMinX() + (bounds.getWidth() / 2)) - (splashImage.getWidth() / 2));
	    initStage.setY((bounds.getMinY() + (bounds.getHeight() / 2)) - (splashImage.getHeight() / 2));
	    Image chartUpColorSmall = ResourceUtils.getImage(StatusBar.class,
		    "icons/FatCow_Icons16x16/information.png");
	    Image chartUpColorBig = ResourceUtils.getImage(StatusBar.class, "icons/FatCow_Icons32x32/information.png");
	    initStage.getIcons().addAll(chartUpColorSmall, chartUpColorBig);
	    initStage.setTitle("ToolShed");
	    initStage.setAlwaysOnTop(true);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public void startApplication() {
	// startStage.accept(initStage);
	initStage.show();
	Task<Void> task = new Task<>() {
	    @Override
	    protected Void call() throws Exception {
		int taskNum = tasks.size();
		for (int taskId = 0; taskId < taskNum; taskId++) {
		    int num = taskId + 1;
		    Task<?> task = tasks.get(taskId);
		    FXThreads.proceedOnFXThread(() -> {
			amountLabel.setText("(" + num + "/" + taskNum + ")");
			titleLabel.setText(task.getTitle());
			Bounds cellBounds = root.getCellBounds(0, 1);
			loadProgress.setPrefWidth(cellBounds.getWidth());
		    });
		    task.run();
		    FXThreads.proceedOnFXThread(() -> loadProgress.setProgress(((double) num) / taskNum));
		}
		return null;
	    }
	};
	task.stateProperty().addListener((observableValue, oldState, newState) -> {
	    if (newState == Worker.State.SUCCEEDED) {
		initStage.setAlwaysOnTop(false);
		startStage.accept(initStage);
	    } // todo add code to gracefully handle other task states.
	});
	FXThreads.runAsync(task);
    }

    public void addTask(String title, Runnable runnable) {
	tasks.add(new Task<Void>() {
	    {
		updateTitle(title);
	    }

	    @Override
	    protected Void call() throws Exception {
		runnable.run();
		return null;
	    }
	});
    }

    public <T> void addTask(String title, Callable<T> callable) {
	tasks.add(new Task<T>() {
	    {
		updateTitle(title);
	    }

	    @Override
	    protected T call() throws Exception {
		return callable.call();
	    }
	});
    }

    public <T> void addTask(Task<T> task) {
	tasks.add(task);
    }
}
