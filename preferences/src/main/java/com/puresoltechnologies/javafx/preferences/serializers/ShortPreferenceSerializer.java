package com.puresoltechnologies.javafx.preferences.serializers;

import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;
import com.puresoltechnologies.javafx.preferences.PreferencesSerializer;

public class ShortPreferenceSerializer implements PreferencesSerializer<Short> {

    @Override
    public boolean isSuitable(PropertyDefinition<Short> definition) {
	return Boolean.class.equals(definition.getType());
    }

    @Override
    public String serialize(Short object) {
	return object.toString();
    }

    @Override
    public Short deserialize(PropertyDefinition<Short> definition, String string) {
	return Short.parseShort(string);
    }
}
