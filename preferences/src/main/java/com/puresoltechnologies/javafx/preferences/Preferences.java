package com.puresoltechnologies.javafx.preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Preferences {

    public static final String PREFERENCES_DIRECTORY_PROPERTY = Preferences.class.getPackageName() + ".directory";
    private final Map<String, ObjectProperty<?>> preferencesValues = new HashMap<>();
    private static Preferences instance = null;

    public static void initialize() throws IOException {
	if (instance != null) {
	    throw new IllegalStateException("Preferences were already initialized.");
	}
	String directoryString = System.getProperty(PREFERENCES_DIRECTORY_PROPERTY);
	if ((directoryString == null) || (directoryString.isEmpty())) {
	    directoryString = System.getProperty("user.dir") + File.separator + ".javafx" + File.separator
		    + "preferences";
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

    public <T> T getValue(PropertyDefinition<T> definition) {
	return getValueProperty(definition).get();
    }

    public <T> ObjectProperty<T> getValueProperty(PropertyDefinition<T> definition) {
	@SuppressWarnings("unchecked")
	ObjectProperty<T> value = (ObjectProperty<T>) preferencesValues.get(definition.getId());
	if (value == null) {
	    T t = readValue(definition);
	    value = new SimpleObjectProperty<>(definition.getDefaultValue());
	    preferencesValues.put(definition.getId(), value);
	}
	return value;
    }

    private <T> T readValue(PropertyDefinition<T> definition) {
	File file = new File(directory, definition.getId());
	if (!file.exists()) {
	    return null;
	}
	try (FileInputStream fileInputStream = new FileInputStream(file);
		ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)) {
	    @SuppressWarnings("unchecked")
	    T t = (T) inputStream.readObject();
	    return t;
	} catch (IOException | ClassNotFoundException e) {
	    return null;
	}
    }

    public <T> T setValue(PropertyDefinition<T> definition, T newValue) {
	writeValue(definition, newValue);
	@SuppressWarnings("unchecked")
	ObjectProperty<T> value = (ObjectProperty<T>) preferencesValues.get(definition.getId());
	T oldValue = null;
	if (value == null) {
	    value = new SimpleObjectProperty<>(definition.getDefaultValue());
	    preferencesValues.put(definition.getId(), value);
	} else {
	    oldValue = value.get();
	    value.set(newValue);
	}
	return oldValue;
    }

    private <T> void writeValue(PropertyDefinition<T> definition, T value) {
	File file = new File(directory, definition.getId());
	if (file.exists()) {
	    file.delete();
	}
	try (FileOutputStream fileOutputStream = new FileOutputStream(file);
		ObjectOutputStream inputStream = new ObjectOutputStream(fileOutputStream)) {
	    inputStream.writeObject(value);
	} catch (IOException e) {
	    // intentionally left empty
	}
    }
}
