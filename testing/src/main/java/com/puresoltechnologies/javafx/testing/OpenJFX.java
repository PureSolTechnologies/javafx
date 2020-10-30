package com.puresoltechnologies.javafx.testing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

/**
 * This class is used to start and stop the OpenJFX platform.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class OpenJFX {

    public static void startOpenJFX() throws InterruptedException {
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

    public static void stopOpenJFX() {
        assertFalse(Platform.isFxApplicationThread(), "FX thread is not correct here.");
        System.out.println("Stopping OpenJFX...");
        OpenJFXRobot.shutdown();
        Platform.exit();
        System.out.println("OpenJFX stopped.");
    }

    /**
     * Private constructor to avoid instantiation.
     */
    private OpenJFX() {
    }
}
