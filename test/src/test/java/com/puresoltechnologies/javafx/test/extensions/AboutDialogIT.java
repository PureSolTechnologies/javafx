package com.puresoltechnologies.javafx.test.extensions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import com.puresoltechnologies.javafx.extensions.dialogs.AboutDialog;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
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
	Thread.sleep(1000);
	clickOn("OK", MouseButton.PRIMARY);
	Thread.sleep(250);
    }

}
