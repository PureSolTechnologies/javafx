package com.puresoltechnologies.javafx.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Platform;

/**
 * This class is a utility class to handle threading in JavaFX applications.
 * <p>
 * This utility class is meant as a convenience class and single point of
 * responsibility to handle threading issues.
 *
 * @author Rick-Rainer Ludwig
 */
public class FXThreads {

    /**
     * This is the thread pool to be used for asynchronous tasks.
     */
    private static ExecutorService threadPool = null;

    /**
     * This method is used to initialize this class and its thread pool.
     *
     * @throws IllegalStateException is thrown in case this class was already
     *                               intialized.
     */
    public static synchronized void initialize() {
	if (threadPool != null) {
	    throw new IllegalStateException("FXThreads was already initialized.");
	}
	threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {

	    private final AtomicInteger count = new AtomicInteger(0);

	    @Override
	    public Thread newThread(Runnable r) {
		int id = count.incrementAndGet();
		return new Thread(r, "FXThreads-pool-thread-" + id);
	    }
	});
    }

    /**
     * This method is used to shut down this class and its thread pool.
     *
     * @throws IllegalStateException is thrown in case this class was already shut
     *                               down.
     */
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

    /**
     * This method puts the provided {@link Runnable} onto the FX thread. It is
     * equivalent to run {@link Platform#runLater(Runnable)}.
     *
     * @param runnable is the {@link Runnable} to run on FX thread.
     */
    public static void runOnFXThread(Runnable runnable) {
	Platform.runLater(runnable);
    }

    /**
     * This method checks whether the call is coming from FX thread. If yes, it is
     * run synchronously. Otherwise, it is run on FX thread. It is used to assure FX
     * thread run, but without overhead to create a new thread in case, it is called
     * on FX thread already.
     *
     * @param runnable is the {@link Runnable} to run on FX thread.
     */
    public static void proceedOnFXThread(Runnable runnable) {
	if (Platform.isFxApplicationThread()) {
	    runnable.run();
	} else {
	    Platform.runLater(runnable);
	}
    }

    /**
     * Runs the provided {@link FutureTask} asynchronously in the
     * {@link #threadPool}.
     *
     * @param <V>  is the return value of the task.
     * @param task is the task to run.
     * @return A {@link Future} is returned to check for finish of the task and
     *         retrieval of the result value.
     */
    public static <V> Future<V> runAsync(FutureTask<V> task) {
	return runAsync(() -> {
	    task.run();
	    return task.get();
	});
    }

    /**
     * Runs the provided {@link Runnable} asynchronously in the {@link #threadPool}.
     *
     * @param runnable is the task to run.
     * @return A {@link Future} is returned to check for finish of the task and
     *         retrieval of the result value.
     */
    public static Future<?> runAsync(Runnable runnable) {
	return threadPool.submit(runnable);
    }

    /**
     * Runs the provided {@link Callable} asynchronously in the {@link #threadPool}.
     *
     * @param <V>      is the return value of the task.
     * @param callable is the task to run.
     * @return A {@link Future} is returned to check for finish of the task and
     *         retrieval of the result value.
     */
    public static <V> Future<V> runAsync(Callable<V> callable) {
	return threadPool.submit(callable);
    }

    /**
     * Private constructor to avoid instantiation.
     */
    private FXThreads() {
    }
}
