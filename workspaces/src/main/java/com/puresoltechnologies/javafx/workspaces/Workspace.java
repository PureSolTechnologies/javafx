package com.puresoltechnologies.javafx.workspaces;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.application.Application;

public class Workspace {

    private static Workspace instance = null;

    public static void launchApplicationInWorkspace(Class<? extends Application> applicationClass, String[] args)
	    throws InterruptedException {
	WorkspaceSettings workspaceSettings = new WorkspaceSettings();
	do {
	    workspaceSettings.setRestarting(false);
	    workspaceSettings.writeSettings();
	    File workspaceDirectory;
	    if (workspaceSettings.isDefault()) {
		workspaceDirectory = workspaceSettings.getDirectory();
	    } else {
		Thread thread = new Thread(() -> Application.launch(WorkspaceSelectionApplication.class, args));
		ClassLoader cl = new ClassLoader() {
		};
		thread.setContextClassLoader(cl);
		thread.run();
		thread.join();
		workspaceSettings.readSettings();
		workspaceDirectory = workspaceSettings.getDirectory();
	    }
	    try {
		initialize(workspaceDirectory);
	    } catch (IOException e) {
		throw new RuntimeException(e);
	    }
	    Application.launch(applicationClass, args);
	    shutdown();
	} while (workspaceSettings.isRestarting());
    }

    /**
     * This method is used to initialize once the {@link Preferences} service.
     * Without initialization, the service cannot be used.
     * 
     * @throws IOException
     */
    static void initialize(File directory) throws IOException {
	if (isInitialized()) {
	    throw new IllegalStateException("Workspace is already initialized.");
	}
	instance = new Workspace(directory);
    }

    static void shutdown() {
	if (!isInitialized()) {
	    throw new IllegalStateException("Workspace is not initialized.");
	}
	instance = null;
    }

    public static boolean isInitialized() {
	return instance != null;
    }

    public static Workspace getInstance() {
	if (instance == null) {
	    throw new IllegalStateException("Workspace is not initialized.");
	}
	return instance;
    }

    private final File directory;

    private Workspace(File directory) {
	this.directory = directory;
    }
}
