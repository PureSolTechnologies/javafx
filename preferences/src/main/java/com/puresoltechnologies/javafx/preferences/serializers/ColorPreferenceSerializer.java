package com.puresoltechnologies.javafx.preferences.serializers;

import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;
import com.puresoltechnologies.javafx.preferences.PreferencesSerializer;

import javafx.scene.paint.Color;

public class ColorPreferenceSerializer implements PreferencesSerializer<Color> {

    @Override
    public boolean isSuitable(PropertyDefinition<Color> definition) {
	return Color.class.equals(definition.getType());
    }

    @Override
    public String serialize(Color object) {
	return object.toString();
    }

    @Override
    public Color deserialize(PropertyDefinition<Color> definition, String string) {
	return Color.valueOf(string);
    }
}
