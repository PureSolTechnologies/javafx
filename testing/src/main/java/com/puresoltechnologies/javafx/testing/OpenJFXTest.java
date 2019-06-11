package com.puresoltechnologies.javafx.testing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.puresoltechnologies.javafx.testing.select.NodeSelection;
import com.puresoltechnologies.javafx.testing.select.NodeSelectionImpl;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.stage.Stage;

public abstract class OpenJFXTest {

    @BeforeAll
    public static void startJavaFX() throws InterruptedException {
	CountDownLatch latch = new CountDownLatch(1);
	Platform.startup(() -> {
	    try {
		OpenJFXRobot.initialize();
	    } finally {
		latch.countDown();
	    }
	});
	assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    @AfterAll
    public static void stopJavaFX() {
	Platform.exit();
    }

    private Stage stage;

    @BeforeEach
    public void setupStage() throws InterruptedException {
	CountDownLatch latch = new CountDownLatch(1);
	Platform.runLater(() -> {
	    try {
		Stage stage = start();
		setStage(stage);
		stage.show();
	    } finally {
		latch.countDown();
	    }
	});
	assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    @AfterEach
    public void destroyStage() {
	Platform.runLater(() -> {
	    stage.hide();
	    stage = null;
	});
    }

    protected abstract Stage start();

    protected abstract void stop();

    public final Stage getStage() {
	return stage;
    }

    public final void setStage(Stage stage) {
	this.stage = stage;
    }

    public final Parent getParentNode() {
	return stage.getScene().getRoot();
    }

    public Parent getNode() {
	return getParentNode();
    }

    public NodeSelection<Parent> nodeSelection() {
	return new NodeSelectionImpl<>(getParentNode());
    }
}
