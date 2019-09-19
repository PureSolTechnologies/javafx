package com.puresoltechnologies.javafx.charts;

import javafx.geometry.Rectangle2D;
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
     * @param canvas   is the {@link Canvas} to render into.
     * @param location is the position and size of the drawing area.
     */
    void renderTo(Canvas canvas, Rectangle2D location);

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
    default void renderTo(Canvas canvas, double x, double y, double width, double height) {
	if ((width > 0) && (height > 0)) {
	    // Rendering only needed when an area with a size greater zero is provided.
	    Rectangle2D location = new Rectangle2D(x, y, width, height);
	    renderTo(canvas, location);
	}
    }

    /**
     * This method returns the last rendering location. This can be used to locate
     * the rendered element later on for instance for handling mouse events.
     *
     * @return A {@link Rectangle2D} is returned containing the last rendering area.
     */
    Rectangle2D getLocation();
}
