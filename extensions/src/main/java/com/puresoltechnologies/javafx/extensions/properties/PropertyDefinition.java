package com.puresoltechnologies.javafx.extensions.properties;

import java.util.Optional;

/**
 * This is the central interface for all preferences property definitions.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <T>
 *            is the actual type of the property value.
 */
public interface PropertyDefinition<T> {

    /**
     * Returns the actual property id as known from property files and
     * {@link System#getProperty(String)}.
     * 
     * @return
     */
    public String getId();

    /**
     * Returns a user readable name of the property.
     * 
     * @return
     */
    public String getName();

    /**
     * Returns a short description to be used in tooltips.
     * 
     * @return
     */
    public String getDescription();

    /**
     * Returns the actual type {@link Class} of the property.
     * 
     * @return
     */
    public Class<T> getType();

    /**
     * Returns the default value if not value can be found saved somewhere.
     * 
     * @return
     */
    public T getDefaultValue();

    /**
     * Defines whether the property is readonly or not. If is not editable, it is
     * only shown, but cannot be altered.
     * 
     * @return
     */
    public boolean isEditable();

    /**
     * This method returns an optiona editor in case there is no standard editor
     * available.
     * 
     * @return
     */
    public Optional<PropertyEditor<T>> getEditor();

}
