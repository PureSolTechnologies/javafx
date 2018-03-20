package com.puresoltechnologies.javafx.preferences.serializers;

import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;
import com.puresoltechnologies.javafx.preferences.PreferencesSerializer;

public class StringPreferenceSerializer implements PreferencesSerializer<String> {

    @Override
    public boolean isSuitable(PropertyDefinition<String> definition) {
	return String.class.equals(definition.getType());
    }

    @Override
    public String serialize(String object) {
	return object;
    }

    @Override
    public String deserialize(PropertyDefinition<String> definition, String string) {
	return string;
    }
}
