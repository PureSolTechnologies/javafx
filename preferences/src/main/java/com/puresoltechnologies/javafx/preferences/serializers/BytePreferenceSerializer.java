package com.puresoltechnologies.javafx.preferences.serializers;

import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;
import com.puresoltechnologies.javafx.preferences.PreferencesSerializer;

public class BytePreferenceSerializer implements PreferencesSerializer<Byte> {

    @Override
    public boolean isSuitable(PropertyDefinition<Byte> definition) {
	return Byte.class.equals(definition.getType());
    }

    @Override
    public String serialize(Byte object) {
	return object.toString();
    }

    @Override
    public Byte deserialize(PropertyDefinition<Byte> definition, String string) {
	return Byte.parseByte(string);
    }
}
