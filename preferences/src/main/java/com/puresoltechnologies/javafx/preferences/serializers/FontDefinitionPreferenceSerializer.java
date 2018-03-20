package com.puresoltechnologies.javafx.preferences.serializers;

import com.puresoltechnologies.javafx.extensions.fonts.FontDefinition;
import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;
import com.puresoltechnologies.javafx.preferences.PreferencesSerializer;

public class FontDefinitionPreferenceSerializer implements PreferencesSerializer<FontDefinition> {

    @Override
    public boolean isSuitable(PropertyDefinition<FontDefinition> definition) {
	return FontDefinition.class.equals(definition.getType());
    }

    @Override
    public String serialize(FontDefinition object) {
	return object.toString();
    }

    @Override
    public FontDefinition deserialize(PropertyDefinition<FontDefinition> definition, String string) {
	return FontDefinition.valueOf(string);
    }
}
