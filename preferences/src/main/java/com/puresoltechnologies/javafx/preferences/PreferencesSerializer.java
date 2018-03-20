package com.puresoltechnologies.javafx.preferences;

import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;

/**
 * This interface is used to provide the {@link Preferences} services with
 * serializers (and de-serializers) to write and read the settings of the disk.
 * 
 * @author Rick-Rainer Ludwig
 */
public interface PreferencesSerializer<T> {

    public boolean isSuitable(PropertyDefinition<T> definition);

    public String serialize(T object);

    public T deserialize(PropertyDefinition<T> definition, String string);

}
