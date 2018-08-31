package com.puresoltechnologies.javafx.extensions.splash;

import java.io.IOException;
import java.util.function.Consumer;

import com.puresoltechnologies.javafx.utils.FXThreads;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreen extends Stage {

    private final Stage applicationStage;
    private final Consumer<Stage> startStage;

    public SplashScreen(Stage applicationStage, Image splashImage, Consumer<Stage> startStage) {
	super();
	this.applicationStage = applicationStage;
	this.startStage = startStage;
	try {
	    ImageView imageView = new ImageView(splashImage);

	    ProgressBar loadProgress = new ProgressBar();
	    loadProgress.setMinWidth(splashImage.getWidth() - 20);
	    loadProgress.setPrefWidth(splashImage.getWidth() - 20);
	    loadProgress.setProgress(0.5);

	    BorderPane root = new BorderPane();
	    BackgroundImage backgroundImage = new BackgroundImage(splashImage, BackgroundRepeat.NO_REPEAT,
		    BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
		    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
	    Background background = new Background(backgroundImage);
	    root.setBackground(background);
	    root.setEffect(new DropShadow());

	    root.setCenter(imageView);
	    root.setBottom(loadProgress);

	    Scene scene = new Scene(root, splashImage.getWidth(), splashImage.getHeight());
	    setScene(scene);

	    final Rectangle2D bounds = Screen.getPrimary().getBounds();
	    // setMinWidth(SPLASH_WIDTH);
	    // setMinHeight(SPLASH_HEIGHT);
	    setX(bounds.getMinX() + bounds.getWidth() / 2 - splashImage.getWidth() / 2);
	    setY(bounds.getMinY() + bounds.getHeight() / 2 - splashImage.getHeight() / 2);
	    Image chartUpColorSmall = ResourceUtils.getImage(this, "/icons/FatCow_Icons16x16/bug_fixing.png");
	    Image chartUpColorBig = ResourceUtils.getImage(this, "/icons/FatCow_Icons32x32/bug_fixing.png");
	    getIcons().addAll(chartUpColorSmall, chartUpColorBig);
	    setTitle("ToolShed");

	    initStyle(StageStyle.TRANSPARENT);
	    setAlwaysOnTop(true);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public void startApplication() {
	applicationStage.hide();
	show();
	Thread thread = new Thread(() -> {
	    try {
		Thread.sleep(5000);
		FXThreads.runOnFXThread(() -> {
		    startStage.accept(applicationStage);
		    applicationStage.show();
		    hide();
		});
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	});
	thread.start();
    }
}
