package com.puresoltechnologies.javafx.charts;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;

/**
 * This is the minimal abstract implementation of a {@link Renderer}.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public abstract class AbstractRenderer implements Renderer {

    /**
     * This method is used to calculate the x position of a given value based on the
     * plotting area.
     *
     * @see Renderer#renderTo(Canvas, double, double, double, double)
     *
     * @param x     is the x position of the plotting area.
     * @param width is the width of the plotting area.
     * @param min   is the minimum x value in the data set to be drawn.
     * @param max   is the maximum x value in the data set to be drawn.
     * @param value is the actual value to be plotted. This value is support to be
     *              larger or equal to min and smaller or equal to max.
     * @return The x position of the value within the given area and for the value
     *         range is returned.
     */
    public static final double calcPosX(double x, double width, double min, double max, double value) {
	if ((max - min) == 0.0) {
	    return x + (width / 2.0);
	}
	return x + ((width / (max - min)) * (value - min));
    }

    /**
     * This method is used to calculate the y position of a given value based on the
     * plotting area.
     *
     * @see Renderer#renderTo(Canvas, double, double, double, double)
     *
     * @param y      is the y position of the plotting area.
     * @param height is the height of the plotting area.
     * @param min    is the minimum y value in the data set to be drawn.
     * @param max    is the maximum y value in the data set to be drawn.
     * @param value  is the actual value to be plotted. This value is support to be
     *               larger or equal to min and smaller or equal to max.
     * @return The y position of the value within the given area and for the value
     *         range is returned.
     */
    public static final double calcPosY(double y, double height, double min, double max, double value) {
	if ((max - min) == 0.0) {
	    return y + (height / 2.0);
	}
	return (y + height) - ((height / (max - min)) * (value - min));
    }

    private Rectangle2D location = null;

    @Override
    public final void renderTo(Canvas canvas, Rectangle2D location) {
	this.location = null; // in case draw crashes...
	draw(canvas, location.getMinX(), location.getMinY(), location.getWidth(), location.getHeight());
	this.location = location;
    }

    /**
     * This method is to be implemented by a child class to perform the actual
     * drawing of the content.
     *
     * @param canvas is the {@link Canvas} to render into.
     * @param x      is the x position of the upper left corner.
     * @param y      is the y position of the upper left corner.
     * @param width  is the width of the area to plot into.
     * @param height is the height of the area to plot into.
     */
    protected abstract void draw(Canvas canvas, double x, double y, double width, double height);

    @Override
    public final Rectangle2D getLocation() {
	return location;
    }
}
