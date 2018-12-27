package com.puresoltechnologies.javafx.test.charts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import com.puresoltechnologies.javafx.preferences.dialogs.PreferencesDialog;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class PreferencesIT extends ApplicationTest {

    @Override
    public void start(Stage stage) {
	Scene scene = new Scene(new Label("PreferencesDialog test running."), 800, 600);
	stage.setScene(scene);
	stage.show();
    }

    @Test
    public void testPerspectiveDialog() throws InterruptedException {
	Thread.sleep(1000);
	Platform.runLater(() -> {
	    PreferencesDialog dialog = new PreferencesDialog();
	    dialog.showAndWait();
	});
	Thread.sleep(1000);
	clickOn("Charts", MouseButton.PRIMARY);
	Thread.sleep(1000);
	clickOn("Cancel", MouseButton.PRIMARY);
	Thread.sleep(5000);
    }

}
