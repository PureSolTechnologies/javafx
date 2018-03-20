package com.puresoltechnologies.javafx.preferences.serializers;

import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;
import com.puresoltechnologies.javafx.preferences.PreferencesSerializer;

public class LongPreferenceSerializer implements PreferencesSerializer<Long> {

    @Override
    public boolean isSuitable(PropertyDefinition<Long> definition) {
	return Long.class.equals(definition.getType());
    }

    @Override
    public String serialize(Long object) {
	return object.toString();
    }

    @Override
    public Long deserialize(PropertyDefinition<Long> definition, String string) {
	return Long.parseLong(string);
    }
}
