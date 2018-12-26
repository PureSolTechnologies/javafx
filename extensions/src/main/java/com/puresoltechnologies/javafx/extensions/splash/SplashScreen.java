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
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SplashScreen {

    private final Stage initStage;
    private final Consumer<Stage> startStage;
    private final Scene scene;
    private final BorderPane root = new BorderPane();

    private final List<Task<?>> tasks = new ArrayList<>();

    public SplashScreen(Stage initStage, Image splashImage, Consumer<Stage> startStage) {
	super();
	this.initStage = initStage;
	this.startStage = startStage;
	try {
	    ImageView imageView = new ImageView(splashImage);

	    ProgressBar loadProgress = new ProgressBar();
	    loadProgress.setMinWidth(splashImage.getWidth() - 20);
	    loadProgress.setPrefWidth(splashImage.getWidth() - 20);
	    loadProgress.setProgress(0.5);

	    BackgroundImage backgroundImage = new BackgroundImage(splashImage, BackgroundRepeat.NO_REPEAT,
		    BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
		    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
	    Background background = new Background(backgroundImage);
	    root.setBackground(background);
	    root.setEffect(new DropShadow());

	    root.setCenter(imageView);
	    root.setBottom(loadProgress);

	    scene = new Scene(root, splashImage.getWidth(), splashImage.getHeight(), Color.TRANSPARENT);
	    initStage.setScene(scene);

	    final Rectangle2D bounds = Screen.getPrimary().getBounds();
	    initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - splashImage.getWidth() / 2);
	    initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - splashImage.getHeight() / 2);
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
	Task<Void> task = new Task<Void>() {
	    @Override
	    protected Void call() throws Exception {
		for (Task<?> task : tasks) {
		    task.run();
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

    public void addTask(Runnable runnable) {
	tasks.add(new Task<Void>() {
	    @Override
	    protected Void call() throws Exception {
		runnable.run();
		return null;
	    }
	});
    }

    public <T> void addTask(Callable<T> callable) {
	tasks.add(new Task<T>() {
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
