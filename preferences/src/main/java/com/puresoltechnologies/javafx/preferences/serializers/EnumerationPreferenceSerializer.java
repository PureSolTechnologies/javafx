package com.puresoltechnologies.javafx.preferences.serializers;

import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;
import com.puresoltechnologies.javafx.preferences.PreferencesSerializer;

public class EnumerationPreferenceSerializer<T extends Enum<T>> implements PreferencesSerializer<T> {

    @Override
    public boolean isSuitable(PropertyDefinition<T> definition) {
	return definition.getType().isEnum();
    }

    @Override
    public String serialize(T object) {
	return object.name();
    }

    @Override
    public T deserialize(PropertyDefinition<T> definition, String string) {
	return Enum.valueOf(definition.getType(), string);
    }
}
