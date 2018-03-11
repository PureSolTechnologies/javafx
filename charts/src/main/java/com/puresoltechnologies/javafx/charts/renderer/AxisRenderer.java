package com.puresoltechnologies.javafx.charts.renderer;

/**
 * General interface for axis renderers.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public interface AxisRenderer {

    /**
     * Returns the thickness of the rendered axis. For x axes, this is the actual
     * height and for y axes the width. The other dimension is specified by the
     * plot.
     * 
     * @return The thickness of the axis.
     */
    public double getTickness();

    public void renderTo(double x, double y, double width, double height);

}
