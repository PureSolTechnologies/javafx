package com.puresoltechnologies.javafx.charts.plots;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Color;

/**
 * This interface is used for all charts which draw lines to represent data.
 * This interface provides the API to handle these.
 *
 * @author Rick-Rainer Ludwig
 */
public interface LinePlot<X extends Comparable<X>, Y extends Comparable<Y>, D extends PlotDatum<X, Y>>
	extends PointBasedPlot<X, Y, D> {

    ObjectProperty<Color> lineColorProperty();

    /**
     * This method is used to set an alternative color for the line of a line plot.
     * In case this color is set to <code>null</code>, the normal plot color is
     * used.
     *
     * @param color is the {@link Color} to be used for the lines in the plot or
     *              <code>null</code> when the normal plot color is to be used.
     */
    default void setLineColor(Color color) {
	lineColorProperty().set(color);
    }

    /**
     * This method is used to set an alternative color for the line of a line plot.
     * In case this color is set to <code>null</code>, the normal plot color is
     * used.
     *
     * @return {@link Color} is returned which is to be used for the lines in the
     *         plot or <code>null</code> when the normal plot color is to be used.
     */
    default Color getLineColor() {
	Color color = lineColorProperty().get();
	return color != null ? color : getColor();
    }

    DoubleProperty lineWidthProperty();

    default void setLineWidth(double width) {
	lineWidthProperty().set(width);
    }

    default double getLineWidth() {
	return lineWidthProperty().get();
    }

    DoubleProperty lineAlphaProperty();

    default void setLineAlpha(double alpha) {
	lineAlphaProperty().set(alpha);
    }

    default double getLineAlpha() {
	return lineAlphaProperty().get();
    }

}
