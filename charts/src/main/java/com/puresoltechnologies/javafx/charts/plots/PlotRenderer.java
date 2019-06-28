package com.puresoltechnologies.javafx.charts.plots;

import com.puresoltechnologies.javafx.charts.Renderer;
import com.puresoltechnologies.javafx.charts.axes.AxisRenderer;

/**
 * This is a marker interface for plot renderer. It is used to distinguish
 * different kinds of plotters.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public interface PlotRenderer<X extends Comparable<X>, Y extends Comparable<Y>, D, XAR extends AxisRenderer<?>, YAR extends AxisRenderer<?>>
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

}
