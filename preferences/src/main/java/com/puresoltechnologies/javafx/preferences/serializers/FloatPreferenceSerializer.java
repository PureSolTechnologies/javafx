package com.puresoltechnologies.javafx.preferences.serializers;

import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;
import com.puresoltechnologies.javafx.preferences.PreferencesSerializer;

public class FloatPreferenceSerializer implements PreferencesSerializer<Float> {

    @Override
    public boolean isSuitable(PropertyDefinition<Float> definition) {
	return Boolean.class.equals(definition.getType());
    }

    @Override
    public String serialize(Float object) {
	return object.toString();
    }

    @Override
    public Float deserialize(PropertyDefinition<Float> definition, String string) {
	return Float.parseFloat(string);
    }
}
