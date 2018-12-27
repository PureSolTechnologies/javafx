package com.puresoltechnologies.javafx.test.perspectives;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import com.puresoltechnologies.javafx.perspectives.PerspectiveContainer;
import com.puresoltechnologies.javafx.perspectives.PerspectiveService;

import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class PerspectivesIT extends ApplicationTest {

    PerspectiveContainer perspectiveContainer;

    @BeforeAll
    public static void initialize() {
	PerspectiveService.initialize();
    }

    @AfterAll
    public static void shutdown() {
	PerspectiveService.shutdown();
    }

    @Override
    public void start(Stage stage) {
	perspectiveContainer = PerspectiveService.getContainer();
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
