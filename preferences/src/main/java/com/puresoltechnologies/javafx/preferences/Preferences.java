package com.puresoltechnologies.javafx.preferences;

import java.io.File;
import java.io.IOException;

public class Preferences {

    public static final String PREFERENCES_DIRECTORY_PROPERTY = Preferences.class.getPackageName() + ".directory";
    private static Preferences instance = null;

    public static void initialize() throws IOException {
	if (instance != null) {
	    throw new IllegalStateException("Preferences were already initialized.");
	}
	String directoryString = System.getProperty(PREFERENCES_DIRECTORY_PROPERTY);
	if ((directoryString == null) || (directoryString.isEmpty())) {
	    directoryString = System.getProperty("user.dir") + File.separator + ".javafx.prefs";
	}
	File directory = new File(directoryString);
	if (!directory.exists()) {
	    if (!directory.mkdirs()) {
		throw new IOException("Could not create preferences directory '" + directoryString + "'.");
	    }
	} else {
	    if (!directory.isDirectory()) {
		throw new IOException("Preferences directory '" + directoryString + "' is not a directory.");
	    }
	}
	instance = new Preferences(directory);
    }

    public static void shutdown() {
	if (instance != null) {
	    throw new IllegalStateException("Preferences are not initialized.");
	}
	instance = null;
    }

    public Preferences getInstance() {
	return instance;
    }

    private final File directory;

    public Preferences(File directory) {
	this.directory = directory;
    }

}
