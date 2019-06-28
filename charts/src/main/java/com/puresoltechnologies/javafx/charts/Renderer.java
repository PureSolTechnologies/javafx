package com.puresoltechnologies.javafx.charts;

/**
 * This is the generic interface for renderer. The canvas to render to, should
 * be provided via constructor. The renderer is just told to render its content
 * to the given rectangle.
 *
 * @author Rick-Rainer Ludwig
 */
public interface Renderer {

    /**
     * This method is called to trigger the renderer to draw its content into the
     * given rectangle.
     *
     * @param x      is the x position of the upper left corner.
     * @param y      is the y position of the upper left corner.
     * @param width  is the width of the area to plot into.
     * @param height is the height of the area to plot into.
     */
    void renderTo(double x, double y, double width, double height);

}
