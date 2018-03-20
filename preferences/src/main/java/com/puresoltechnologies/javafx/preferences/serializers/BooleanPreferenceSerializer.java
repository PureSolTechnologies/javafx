package com.puresoltechnologies.javafx.preferences.serializers;

import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;
import com.puresoltechnologies.javafx.preferences.PreferencesSerializer;

public class BooleanPreferenceSerializer implements PreferencesSerializer<Boolean> {

    @Override
    public boolean isSuitable(PropertyDefinition<Boolean> definition) {
	return Boolean.class.equals(definition.getType());
    }

    @Override
    public String serialize(Boolean object) {
	return object.toString();
    }

    @Override
    public Boolean deserialize(PropertyDefinition<Boolean> definition, String string) {
	return Boolean.parseBoolean(string);
    }
}
