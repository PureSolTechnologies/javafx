package com.puresoltechnologies.javafx.perspectives;

import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;
import com.puresoltechnologies.javafx.extensions.properties.SimplePropertyDefinition;
import com.puresoltechnologies.javafx.perspectives.parts.Part;

import javafx.scene.control.ContentDisplay;

/**
 * This interface contains all constants to chart property definitions related
 * to all {@link Perspective}s and {@link Part}s.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public interface PerspectiveProperties {

    static final String PROPERTY_BASE = PerspectiveProperties.class.getPackage().getName();

    static final ContentDisplay perspectiveToolbarContentDefault = ContentDisplay.GRAPHIC_ONLY;
    static final ContentDisplay partHeaderToolbarContentDefault = ContentDisplay.GRAPHIC_ONLY;

    public static final PropertyDefinition<ContentDisplay> perspectiveToolbarContentDisplay = new SimplePropertyDefinition<>(
	    PROPERTY_BASE + ".background.color", "Background Color", "Background color to be used for charts.",
	    ContentDisplay.class, perspectiveToolbarContentDefault);

    public static final PropertyDefinition<ContentDisplay> partHeaderToolbarContentDisplay = new SimplePropertyDefinition<>(
	    PROPERTY_BASE + ".axis.color", "Axis Color", "Axis color to be used for all plots.", ContentDisplay.class,
	    partHeaderToolbarContentDefault);

}
