package com.puresoltechnologies.javafx.charts.renderer.axis;

import com.puresoltechnologies.javafx.charts.renderer.Renderer;

/**
 * General interface for axis renderers.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public interface AxisRenderer extends Renderer {

    /**
     * Returns the thickness of the rendered axis. For x axes, this is the actual
     * height and for y axes the width. The other dimension is specified by the
     * plot.
     * 
     * @return The thickness of the axis.
     */
    public double getTickness();

}
