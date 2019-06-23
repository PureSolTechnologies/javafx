package com.puresoltechnologies.javafx.testing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.puresoltechnologies.javafx.testing.mouse.MouseInteraction;
import com.puresoltechnologies.javafx.testing.select.ButtonSelector;
import com.puresoltechnologies.javafx.testing.select.MenuSelector;
import com.puresoltechnologies.javafx.testing.select.NodeFullSearch;

import javafx.application.Platform;
import javafx.stage.Stage;

public abstract class OpenJFXTest implements NodeFullSearch, MouseInteraction, ButtonSelector, MenuSelector {

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
    public void destroyStage() throws InterruptedException {
	try {
	    stop();
	} finally {
	    stage = null;
	}
    }

    protected abstract Stage start();

    protected abstract void stop();

    public final Stage getStage() {
	return stage;
    }

    public final void setStage(Stage stage) {
	this.stage = stage;
    }

}
