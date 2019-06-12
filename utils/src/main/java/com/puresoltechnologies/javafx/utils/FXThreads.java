package com.puresoltechnologies.javafx.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

public class FXThreads {

    private static ExecutorService threadPool = null;

    public static synchronized void initialize() {
	if (threadPool != null) {
	    throw new IllegalStateException("FXThreads was already initialized.");
	}
	threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public static synchronized void shutdown() throws InterruptedException {
	if (threadPool == null) {
	    throw new IllegalStateException("FXThreads was not initialized.");
	}
	try {
	    threadPool.shutdown();
	    threadPool.awaitTermination(1, TimeUnit.MINUTES);
	} finally {
	    threadPool = null;
	}
    }

    public static void runOnFXThread(Runnable runnable) {
	Platform.runLater(runnable);
    }

    public static void proceedOnFXThread(Runnable runnable) {
	if (Platform.isFxApplicationThread()) {
	    runnable.run();
	} else {
	    Platform.runLater(runnable);
	}
    }

    public static void proceedOnNonFXThread(Runnable runnable) {
	if (Platform.isFxApplicationThread()) {
	    threadPool.submit(runnable);
	} else {
	    runnable.run();
	}
    }

    public static void runAsync(Runnable runnable) {
	threadPool.execute(runnable);
    }

    public static <V> Future<V> runAsync(Callable<V> callable) {
	return threadPool.submit(callable);
    }

    /**
     * Private constructor to avoid instantiation.
     */
    private FXThreads() {
    }
}
