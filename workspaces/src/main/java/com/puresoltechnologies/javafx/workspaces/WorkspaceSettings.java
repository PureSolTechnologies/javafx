package com.puresoltechnologies.javafx.workspaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.puresoltechnologies.javafx.utils.Settings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class handles reading the workspace settings.
 *
 * @author Rick-Rainer Ludwig
 */
public class WorkspaceSettings {

    private static final String BASE_PROPERTY_NAME = "com.puresoltechnologies.javafx.workspaces";

    private static final String DIRECTORY_PROPERTY = BASE_PROPERTY_NAME + ".selections.current";
    private static final String DEFAULT_SET_PROPERTY = BASE_PROPERTY_NAME + ".selections.use_default";
    private static final String FORMER_SELECTIONS_PROPERTY_BASE = BASE_PROPERTY_NAME + ".selections.former";
    private static final String RESTARTING_PROPERTY_BASE = BASE_PROPERTY_NAME + ".restarting";

    private static final String WORKSPACE_SELECTION_PROPERTIES_FILENAME = "workspace-selection.properties";

    private final Properties properties = new Properties();
    private final ObservableList<File> formerDirectories = FXCollections.observableArrayList();

    public WorkspaceSettings() {
	readSettings();
    }

    private static File getPropertiesFile() throws IOException {
	File directory = Settings.getDirectory();
	return new File(directory, WORKSPACE_SELECTION_PROPERTIES_FILENAME);
    }

    public void readSettings() {
	try {
	    File propertiesFile = getPropertiesFile();
	    if (propertiesFile.exists()) {
		try (FileInputStream fileInputStream = new FileInputStream(propertiesFile)) {
		    properties.clear();
		    properties.load(fileInputStream);
		}
	    }
	    formerDirectories.clear();
	    properties.entrySet().stream() //
		    .filter(entry -> entry.getKey().toString().startsWith(FORMER_SELECTIONS_PROPERTY_BASE))
		    .sorted((o1, o2) -> o1.getKey().toString().compareTo(o2.getKey().toString()))
		    .map(entry -> entry.getValue().toString()) //
		    .map(directoryString -> new File(directoryString)) //
		    .forEach(directory -> formerDirectories.add(directory));
	} catch (IOException e) {
	    throw new RuntimeException("Could not store workspace selection settings.", e);
	}
    }

    public void writeSettings() {
	try {
	    updateFormerDirectories();
	    File propertiesFile = getPropertiesFile();
	    if (!propertiesFile.exists()) {
		if (!propertiesFile.createNewFile()) {
		    throw new IOException(
			    "Could not create a new workspace sellectionn properties file '" + propertiesFile + "'.");
		}
	    }
	    try (FileOutputStream fileOutputStream = new FileOutputStream(propertiesFile)) {
		properties.store(fileOutputStream, "Settings for PureSol Technolgies' JavaFX Workspaces");
	    }
	} catch (IOException e) {
	    throw new RuntimeException("Could not store workspace selection settings.", e);
	}
    }

    public void setDirectory(File directory) {
	properties.setProperty(DIRECTORY_PROPERTY, directory.getAbsolutePath());
    }

    public File getDirectory() {
	return new File(properties.getProperty(DIRECTORY_PROPERTY, System.getProperty("user.home")));
    }

    public boolean isDefault() {
	return Boolean.parseBoolean(properties.getProperty(DEFAULT_SET_PROPERTY, "false"));
    }

    public void setDefault(boolean d) {
	properties.setProperty(DEFAULT_SET_PROPERTY, Boolean.valueOf(d).toString());
    }

    public boolean isRestarting() {
	return Boolean.parseBoolean(properties.getProperty(RESTARTING_PROPERTY_BASE, "false"));
    }

    public void setRestarting(boolean d) {
	properties.setProperty(RESTARTING_PROPERTY_BASE, Boolean.valueOf(d).toString());
    }

    public ObservableList<File> getFormerDirectories() {
	return formerDirectories;
    }

    public void updateFormerDirectories() {
	File currentDirectory = getDirectory();
	if (formerDirectories.contains(currentDirectory)) {
	    formerDirectories.remove(currentDirectory);
	}
	formerDirectories.add(0, currentDirectory);
	for (int i = 0; i < Math.min(10, formerDirectories.size()); i++) {
	    properties.put(FORMER_SELECTIONS_PROPERTY_BASE + "." + i, formerDirectories.get(i).getAbsolutePath());
	}
    }

}
