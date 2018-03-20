package com.puresoltechnologies.javafx.preferences.serializers;

import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;
import com.puresoltechnologies.javafx.preferences.PreferencesSerializer;

public class IntegerPreferenceSerializer implements PreferencesSerializer<Integer> {

    @Override
    public boolean isSuitable(PropertyDefinition<Integer> definition) {
	return Integer.class.equals(definition.getType());
    }

    @Override
    public String serialize(Integer object) {
	return object.toString();
    }

    @Override
    public Integer deserialize(PropertyDefinition<Integer> definition, String string) {
	return Integer.parseInt(string);
    }
}
