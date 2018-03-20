package com.puresoltechnologies.javafx.preferences.serializers;

import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;
import com.puresoltechnologies.javafx.preferences.PreferencesSerializer;

public class DoublePreferenceSerializer implements PreferencesSerializer<Double> {

    @Override
    public boolean isSuitable(PropertyDefinition<Double> definition) {
	return Double.class.equals(definition.getType());
    }

    @Override
    public String serialize(Double object) {
	return object.toString();
    }

    @Override
    public Double deserialize(PropertyDefinition<Double> definition, String string) {
	return Double.parseDouble(string);
    }
}
