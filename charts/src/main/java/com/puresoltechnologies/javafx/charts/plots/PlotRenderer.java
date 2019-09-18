package com.puresoltechnologies.javafx.charts.plots;

import com.puresoltechnologies.javafx.charts.Renderer;
import com.puresoltechnologies.javafx.charts.axes.AxisRenderer;

import javafx.beans.property.DoubleProperty;

/**
 * This is a interface for plot renderer. It is used to distinguish different
 * kinds of plotters.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public interface PlotRenderer<X extends Comparable<X>, Y extends Comparable<Y>, D extends PlotDatum<X, Y>, XAR extends AxisRenderer<?>, YAR extends AxisRenderer<?>>
	extends Renderer {

    /**
     * This method returns the plot associated with this renderer.
     *
     * @return A {@link Plot} is returned.
     */
    Plot<X, Y, D> getPlot();

    /**
     * This method returns the {@link AxisRenderer} for the x axis.
     *
     * @return An {@link AxisRenderer} for the x axis is returned.
     */
    XAR getXAxisRenderer();

    /**
     * This method returns the {@link AxisRenderer} for the y axis.
     *
     * @return An {@link AxisRenderer} for the y axis is returned.
     */
    YAR getYAxisRenderer();

    /**
     * This method returns the property which defines the capture range for data
     * points for method {@link #findDataPoint(double, double)}.
     *
     * @return The property is returned as {@link DoubleProperty}.
     */
    DoubleProperty dataPointCaptureRangeProperty();

    /**
     * This method is used to find the nearest data point in a capture range around
     * the canvas coordinates. The capture range is defined via
     * {@link #dataPointCaptureRangeProperty()}.
     *
     * @param canvasX is the X coordinate in plotting {@link PlotCanvas}.
     * @param canvasY is the Y coordinate in plotting {@link PlotCanvas}.
     * @return The data point nearest to the given coordinates.
     */
    D findDataPoint(double canvasX, double canvasY);
}
