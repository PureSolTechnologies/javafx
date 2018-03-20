package com.puresoltechnologies.javafx.preferences;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This is the central service object for the preferences handling.
 * 
 * @author Rick-Rainer Ludwig
 */
public class Preferences {

    public static final String PREFERENCES_DIRECTORY_PROPERTY = Preferences.class.getPackageName() + ".directory";
    private final Map<String, ObjectProperty<?>> preferencesValues = new HashMap<>();
    private static Preferences instance = null;

    /**
     * This method is used to initialize once the {@link Preferences} service.
     * Without initialization, the service cannot be used.
     * 
     * @throws IOException
     */
    public static void initialize() throws IOException {
	if (isInitialized()) {
	    throw new IllegalStateException("Preferences were already initialized.");
	}
	String directoryString = System.getProperty(PREFERENCES_DIRECTORY_PROPERTY);
	if ((directoryString == null) || (directoryString.isEmpty())) {
	    directoryString = System.getProperty("user.home") + File.separator + ".javafx" + File.separator
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
	if (!isInitialized()) {
	    throw new IllegalStateException("Preferences are not initialized.");
	}
	instance = null;
    }

    public static boolean isInitialized() {
	return instance != null;
    }

    public static Preferences getInstance() {
	if (instance == null) {
	    throw new IllegalStateException("Preferences are not initialized.");
	}
	return instance;
    }

    public static <T> ObjectProperty<T> getProperty(PropertyDefinition<T> definition) {
	return instance.getValueProperty(definition);
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
	    if (t != null) {
		value = new SimpleObjectProperty<>(t);
	    } else {
		value = new SimpleObjectProperty<>(definition.getDefaultValue());
	    }
	    preferencesValues.put(definition.getId(), value);
	}
	return value;
    }

    private <T> T readValue(PropertyDefinition<T> definition) {
	File file = new File(directory, definition.getId());
	if (!file.exists()) {
	    return null;
	}
	PreferencesSerializer<T> serializer = findSerializer(definition);
	try (FileInputStream fileInputStream = new FileInputStream(file)) {
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    byte[] buffer = new byte[256];
	    int size = -1;
	    while ((size = fileInputStream.read(buffer)) != -1) {
		byteArrayOutputStream.write(buffer, 0, size);
	    }
	    String string = new String(byteArrayOutputStream.toByteArray(), Charset.defaultCharset());
	    return serializer.deserialize(definition, string);
	} catch (IOException e) {
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
	PreferencesSerializer<T> serializer = findSerializer(definition);
	try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
	    String serialized = serializer.serialize(value);
	    fileOutputStream.write(serialized.getBytes(Charset.defaultCharset()));
	} catch (IOException e) {
	    // intentionally left empty
	}
    }

    private <T> PreferencesSerializer<T> findSerializer(PropertyDefinition<T> definition) {
	@SuppressWarnings("rawtypes")
	ServiceLoader<PreferencesSerializer> loader = ServiceLoader.load(PreferencesSerializer.class);
	@SuppressWarnings("rawtypes")
	Iterator<PreferencesSerializer> iterator = loader.iterator();
	while (iterator.hasNext()) {
	    @SuppressWarnings("unchecked")
	    PreferencesSerializer<T> next = iterator.next();
	    if (next.isSuitable(definition)) {
		return next;
	    }
	}
	return null;
    }
}
