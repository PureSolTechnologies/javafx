package com.puresoltechnologies.javafx.preferences;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import com.puresoltechnologies.javafx.preferences.dialogs.PreferencesDialog;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class PreferencesTest extends ApplicationTest {

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
	clickOn("Cancel", MouseButton.PRIMARY);
	Thread.sleep(5000);
    }

}
