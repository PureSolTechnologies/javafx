package com.puresoltechnologies.javafx.charts.axes;

import com.puresoltechnologies.javafx.extensions.fonts.FontDefinition;

import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Color;

/**
 * Interface for a single axis.
 *
 * @author Rick-Rainer Ludwig
 */
public interface Axis<T> {

    ObjectProperty<FontDefinition> titleFontProperty();

    default void setTitleFont(FontDefinition titleFont) {
	titleFontProperty().setValue(titleFont);
    }

    default FontDefinition getTitleFont() {
	return titleFontProperty().getValue();
    }

    ObjectProperty<FontDefinition> labelFontProperty();

    default void setLabelFont(FontDefinition labelFont) {
	labelFontProperty().setValue(labelFont);
    }

    default FontDefinition getLabelFont() {
	return labelFontProperty().getValue();
    }

    ObjectProperty<Color> colorProperty();

    default void setColor(Color color) {
	colorProperty().setValue(color);
    }

    default Color getColor() {
	return colorProperty().getValue();
    }

    ObjectProperty<Color> backgroundColorProperty();

    default void setBackgroundColor(Color color) {
	backgroundColorProperty().setValue(color);
    }

    default Color getBackgroundColor() {
	return backgroundColorProperty().getValue();
    }

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
