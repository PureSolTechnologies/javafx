package com.puresoltechnologies.javafx.charts.axes;

import com.puresoltechnologies.javafx.charts.Renderer;

/**
 * General interface for axis renderer.
 *
 * @author Rick-Rainer Ludwig
 * @param <T> is the type of the axis values.
 */
public interface AxisRenderer<T> extends Renderer {

    /**
     * This method returns the axis minimum value.
     *
     * @return The minimum value is returned with the given type
     */
    T getMin();

    /**
     * This method returns the axis maximum value.
     *
     * @return The maximum value is returned with the given type
     */
    T getMax();

    /**
     * Returns the thickness of the rendered axis. For x axes, this is the actual
     * height and for y axes the width. The other dimension is specified by the
     * plot.
     *
     * @return The thickness of the axis.
     */
    double getTickness();

}