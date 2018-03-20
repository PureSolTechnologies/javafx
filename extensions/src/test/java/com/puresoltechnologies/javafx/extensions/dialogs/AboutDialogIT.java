package com.puresoltechnologies.javafx.extensions.dialogs;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class AboutDialogIT extends ApplicationTest {

    @Override
    public void start(Stage stage) {
	Scene scene = new Scene(new Label("AboutDialogIT test running."), 800, 600);
	stage.setScene(scene);
	stage.show();
    }

    @Test
    public void testDialog() throws InterruptedException {
	Platform.runLater(() -> {
	    AboutDialog dialog = new AboutDialog();
	    dialog.showAndWait();
	});
	Thread.sleep(250);
	clickOn("OK", MouseButton.PRIMARY);
	Thread.sleep(250);
    }

}
