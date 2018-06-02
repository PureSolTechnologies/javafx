package com.puresoltechnologies.javafx.charts.axes;

import com.puresoltechnologies.javafx.charts.Renderer;

/**
 * General interface for axis renderers.
 * 
 * @author Rick-Rainer Ludwig
 * @param <T>
 *            is the type of the axis values.
 */
public interface AxisRenderer<T> extends Renderer {

    public T getMin();

    public T getMax();

    /**
     * Returns the thickness of the rendered axis. For x axes, this is the actual
     * height and for y axes the width. The other dimension is specified by the
     * plot.
     * 
     * @return The thickness of the axis.
     */
    public double getTickness();

}