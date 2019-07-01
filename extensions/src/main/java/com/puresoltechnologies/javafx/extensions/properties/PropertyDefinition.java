package com.puresoltechnologies.javafx.extensions.properties;

import java.util.Optional;

/**
 * This is the central interface for all preferences property definitions.
 *
 * @author Rick-Rainer Ludwig
 *
 * @param <T> is the actual type of the property value.
 */
public interface PropertyDefinition<T> {

    /**
     * Returns the actual property id as known from property files and
     * {@link System#getProperty(String)}.
     *
     * @return A {@link String} is returned.
     */
    String getId();

    /**
     * Returns a user readable name of the property.
     *
     * @return A {@link String} is returned.
     */
    String getName();

    /**
     * Returns a short description to be used in tooltips.
     *
     * @return A {@link String} is returned.
     */
    String getDescription();

    /**
     * Returns the actual type {@link Class} of the property.
     *
     * @return A {@link Class} is returned.
     */
    Class<T> getType();

    /**
     * Returns the default value if not value can be found saved somewhere.
     *
     * @return A object of type T is returned.
     */
    T getDefaultValue();

    /**
     * Defines whether the property is readonly or not. If is not editable, it is
     * only shown, but cannot be altered.
     *
     * @return A boolean is returned.
     */
    boolean isEditable();

    /**
     * This method returns an optiona editor in case there is no standard editor
     * available.
     *
     * @return An {@link Optional} of {@link PropertyEditor} is returned.
     */
    Optional<PropertyEditor<T>> getEditor();

}
