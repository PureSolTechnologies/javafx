package com.puresoltechnologies.javafx.workspaces;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.prefs.Preferences;

import com.puresoltechnologies.javafx.workspaces.dialogs.WorkspaceSelectionDialog;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This is the main class to start a JavaFX {@link Application} in a defined
 * Workspace area.
 *
 * Just use {@link #launchApplicationInWorkspace(Class, String[])} method to run
 * the Application. The selection of the workspace directory takes place in this
 * class.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class Workspace {

    private static final StringProperty workspaceTerm = new SimpleStringProperty("Workspace");
    private static Workspace instance = null;

    public static StringProperty workspaceTermProperty() {
	return workspaceTerm;
    }

    public static String getWorkspaceTerm() {
	return workspaceTerm.get();
    }

    public static void setWorkspaceTerm(String workspaceTerm) {
	Workspace.workspaceTerm.set(workspaceTerm);
    }

    public static void launchApplicationInWorkspace(Class<? extends Application> applicationClass, String[] args)
	    throws InterruptedException {
	/*
	 * Set the JavaFX platform to be no closed when all windows are closed. This is
	 * needed, because we want to support restarts for workspace changes.
	 */
	Platform.setImplicitExit(false);
	/*
	 * Run the run-rerun loop...
	 */
	Platform.runLater(() -> runOuterLoop(applicationClass, args));
    }

    private static void runOuterLoop(Class<? extends Application> applicationClass, String[] args) {
	try {
	    WorkspaceSettings workspaceSettings = new WorkspaceSettings();
	    do {
		boolean restarting = workspaceSettings.isRestarting();
		File workspaceDirectory;
		if (restarting) {
		    // Assure no restart is happening again, if there was one...
		    workspaceSettings.setRestarting(false);
		    workspaceSettings.writeSettings();
		    workspaceDirectory = workspaceSettings.getDirectory();
		} else {
		    // Get workspace directory or exit
		    workspaceDirectory = retrieveWorkspaceDirectory(workspaceSettings);
		}
		if (workspaceDirectory == null) {
		    break;
		}
		// Initialize Workspace and run application
		initialize(workspaceDirectory);
		runApplication(applicationClass, args);
		shutdown();
		// Update settings for change made by the application
		workspaceSettings.readSettings();
	    } while (workspaceSettings.isRestarting());
	    Platform.exit();
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    private static File retrieveWorkspaceDirectory(WorkspaceSettings workspaceSettings) {
	File workspaceDirectory = null;
	if (workspaceSettings.isDefault()) {
	    workspaceDirectory = workspaceSettings.getDirectory();
	} else {
	    WorkspaceSelectionDialog dialog = new WorkspaceSelectionDialog();
	    Optional<File> result = dialog.showAndWait();
	    if (result.isPresent()) {
		workspaceDirectory = result.get();
	    }
	}
	return workspaceDirectory;
    }

    private static void runApplication(Class<? extends Application> applicationClass, String[] args) throws Exception {
	// TODO: Because of JDK9, args cannot be registered as it is not allowed to
	// access com.sun.javafx...
	Application application = applicationClass.getDeclaredConstructor().newInstance();
	application.init();
	Stage stage = new Stage(StageStyle.DECORATED);
	application.start(stage);
	if (stage.isShowing()) {
	    stage.hide();
	}
	stage.showAndWait();
	application.stop();
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
