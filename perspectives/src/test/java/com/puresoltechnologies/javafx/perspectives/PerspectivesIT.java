package com.puresoltechnologies.javafx.perspectives;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class PerspectivesIT extends ApplicationTest {

    PerspectiveContainer perspectiveContainer;

    @Override
    public void start(Stage stage) {
	perspectiveContainer = new PerspectiveContainer();
	perspectiveContainer.setId("perspectiveContainer");
	Scene scene = new Scene(perspectiveContainer, 800, 600);
	stage.setScene(scene);
	stage.show();
    }

    @Test
    public void testPerspectiveDialog() throws InterruptedException {
	Thread.sleep(1000);
	clickOn("Open...", MouseButton.PRIMARY);
	Thread.sleep(1000);
	clickOn("OK", MouseButton.PRIMARY);
    }

}
