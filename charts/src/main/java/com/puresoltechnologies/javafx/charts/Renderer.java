package com.puresoltechnologies.javafx.charts;

import javafx.scene.canvas.Canvas;

/**
 * This is the generic interface for a renderer. The renderer is just told to
 * render its content to the given rectangle into a provided {@link Canvas}.
 *
 * @author Rick-Rainer Ludwig
 */
public interface Renderer {

    /**
     * This method is called to trigger the renderer to draw its content into the
     * given rectangle.
     *
     * @param canvas is the {@link Canvas} to render into.
     * @param x      is the x position of the upper left corner.
     * @param y      is the y position of the upper left corner.
     * @param width  is the width of the area to plot into.
     * @param height is the height of the area to plot into.
     */
    void renderTo(Canvas canvas, double x, double y, double width, double height);

}
