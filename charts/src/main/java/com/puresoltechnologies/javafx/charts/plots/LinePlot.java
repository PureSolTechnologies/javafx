package com.puresoltechnologies.javafx.charts.plots;

import javafx.beans.property.DoubleProperty;

/**
 * This interface is used for all charts which draw lines to represent data.
 * This interface provides the API to handle these.
 *
 * @author Rick-Rainer Ludwig
 */
public interface LinePlot {

    DoubleProperty lineWidthProperty();

    default void setLineWidth(double width) {
	lineWidthProperty().set(width);
    }

    default double getLineWidth() {
	return lineWidthProperty().get();
    }

}
