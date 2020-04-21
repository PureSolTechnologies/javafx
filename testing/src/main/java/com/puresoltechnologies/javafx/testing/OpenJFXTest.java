package com.puresoltechnologies.javafx.testing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.puresoltechnologies.javafx.testing.mouse.MouseInteraction;
import com.puresoltechnologies.javafx.testing.select.ButtonSelector;
import com.puresoltechnologies.javafx.testing.select.DialogSelector;
import com.puresoltechnologies.javafx.testing.select.MenuSelector;
import com.puresoltechnologies.javafx.testing.select.NodeFullSearch;
import com.puresoltechnologies.javafx.utils.FXThreads;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * This class is used to extend JUnit5 test classes to get support for JavaFX
 * testing.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public abstract class OpenJFXTest
        implements NodeFullSearch, MouseInteraction, ButtonSelector, MenuSelector, DialogSelector {

    private static final File defaultSnapshotDirectory = new File("target/test-snapshots");

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
                System.out.println("Calling test's start()...");
                stage = start();
                System.out.println("Test's start() called. Showing...");
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
                    System.out.println("Calling test's stop()...");
                    stop();
                    System.out.println("Test's stop() called.");
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

    public final File snapshot() {
        return snapshot(defaultSnapshotDirectory, System.currentTimeMillis() + ".png");
    }

    public final File snapshot(String name) {
        return snapshot(defaultSnapshotDirectory, name);
    }

    public final File snapshot(File directory, String name) {
        if (!directory.exists()) {
            assertTrue(directory.mkdirs(), "Could not created needed snapshot directory '" + directory + "'.");
        } else {
            assertTrue(directory.isDirectory(), "Provided snapshot directory '" + directory + "' is not a directory.");
        }
        Future<File> future = FXThreads.runAsync(() -> {
            WritableImage snapshot = getStage().getScene().getRoot().snapshot(new SnapshotParameters(), null);
            File file = new File(directory, name);
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
            return file;
        });
        try {
            return future.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            fail("Could not store snapshot.", e);
            return null;
        }
    }

}
