package com.puresoltechnologies.javafx.charts.axes;

import com.puresoltechnologies.javafx.extensions.fonts.FontDefinition;

import javafx.beans.property.ObjectProperty;

/**
 * Interface for a single axis.
 *
 * @author Rick-Rainer Ludwig
 */
public interface Axis<T> {

    ObjectProperty<FontDefinition> titleFontProperty();

    void setTitleFont(FontDefinition titleFont);

    FontDefinition getTitleFont();

    ObjectProperty<FontDefinition> labelFontProperty();

    void setLabelFont(FontDefinition labelFont);

    FontDefinition getLabelFont();

    /**
     * For internal use, an identifier is randomly generated to find reused axis for
     * multi plots.
     *
     * @return A {@link String} from a random UUID is returned.
     */
    String getId();

    String getTitle();

    String getUnit();

    AxisType getAxisType();

    Class<T> getValueType();

}
