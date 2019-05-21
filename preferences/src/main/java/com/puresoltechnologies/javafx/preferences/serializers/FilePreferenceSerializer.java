package com.puresoltechnologies.javafx.preferences.serializers;

import java.io.File;

import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;
import com.puresoltechnologies.javafx.preferences.PreferencesSerializer;

public class FilePreferenceSerializer implements PreferencesSerializer<File> {

    @Override
    public boolean isSuitable(PropertyDefinition<File> definition) {
	return File.class.equals(definition.getType());
    }

    @Override
    public String serialize(File object) {
	return object.getPath();
    }

    @Override
    public File deserialize(PropertyDefinition<File> definition, String string) {
	return new File(string);
    }
}
