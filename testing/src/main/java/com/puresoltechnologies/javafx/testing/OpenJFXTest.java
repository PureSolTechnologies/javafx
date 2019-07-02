package com.puresoltechnologies.javafx.testing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.puresoltechnologies.javafx.testing.mouse.MouseInteraction;
import com.puresoltechnologies.javafx.testing.select.ButtonSelector;
import com.puresoltechnologies.javafx.testing.select.DialogSelector;
import com.puresoltechnologies.javafx.testing.select.MenuSelector;
import com.puresoltechnologies.javafx.testing.select.NodeFullSearch;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.stage.Window;

public abstract class OpenJFXTest
	implements NodeFullSearch, MouseInteraction, ButtonSelector, MenuSelector, DialogSelector {

    @BeforeAll
    public static void startJavaFX() throws InterruptedException {
	assertFalse(Platform.isFxApplicationThread(), "FX thread is not correct here.");
	System.out.println("Starting OpenJFX...");
	CountDownLatch latch = new CountDownLatch(1);
	Platform.startup(() -> {
	    try {
		OpenJFXRobot.initialize();
	    } finally {
		latch.countDown();
	    }
	});
	Platform.setImplicitExit(false);
	assertTrue(latch.await(10, TimeUnit.SECONDS), "OpenJFX not started in time.");
	System.out.println("OpenJFX started.");
    }

    @AfterAll
    public static void stopJavaFX() {
	assertFalse(Platform.isFxApplicationThread(), "FX thread is not correct here.");
	System.out.println("Stopping OpenJFX...");
	OpenJFXRobot.shutdown();
	Platform.exit();
	System.out.println("OpenJFX stopped.");
    }

    private Stage stage;

    @BeforeEach
    public void setupStage() throws InterruptedException {
	assertFalse(Platform.isFxApplicationThread(), "FX thread is not correct here.");
	System.out.println("Setting up stage...");
	CountDownLatch latch = new CountDownLatch(1);
	Platform.runLater(() -> {
	    try {
		System.out.println("Calling start()...");
		stage = start();
		System.out.println("start() called. Showing...");
		stage.show();
	    } finally {
		latch.countDown();
	    }
	});
	assertTrue(latch.await(10, TimeUnit.SECONDS), "Stage was not set up in time.");
	System.out.println("Stage set up.");
    }

    @AfterEach
    public void destroyStage() throws InterruptedException {
	assertFalse(Platform.isFxApplicationThread(), "FX thread is not correct here.");
	System.out.println("Destroying stages...");
	try {
	    ObservableList<Window> windows = Window.getWindows();

	    CountDownLatch latch = new CountDownLatch(windows.size() + 1);
	    windows.stream().filter(window -> Stage.class.isAssignableFrom(window.getClass())) //
		    .map(window -> (Stage) window)//
		    .forEach(stage -> Platform.runLater(() -> {
			try {
			    System.out.println("Closing '" + stage.getTitle() + "'...");
			    stage.close();
			    System.out.println("'" + stage.getTitle() + "' closed.");
			} finally {
			    latch.countDown();
			}
		    }));
	    Platform.runLater(() -> {
		try {
		    System.out.println("Calling stop()...");
		    stop();
		    System.out.println("stop() called.");
		} finally {
		    latch.countDown();
		}
	    });
	    assertTrue(latch.await(10, TimeUnit.SECONDS));
	    System.out.println("Stages destroyed.");
	} finally {
	    stage = null;
	}
    }

    protected abstract Stage start();

    protected abstract void stop();

    public final Stage getStage() {
	return stage;
    }

}
