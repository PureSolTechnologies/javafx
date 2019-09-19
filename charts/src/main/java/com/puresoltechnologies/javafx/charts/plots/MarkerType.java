package com.puresoltechnologies.javafx.charts.plots;

import com.puresoltechnologies.javafx.charts.Renderer;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;

/**
 * This enum is used to specify the marker type for a plot.
 *
 * @author Rick-Rainer Ludwig
 */
public enum MarkerType implements Renderer {

    SQUARE(new SquareMarkerRenderer()), //
    CROSS(new CrossMarkerRenderer()), //
    PLUS(new PlusMarkerRenderer()), //
    DOT(new DotMarkerRenderer()), //
    CIRCLE(new CircleMarkerRenderer()), //
    DIAMOND(new DiamondMarkerRenderer()), //
    ;

    private final Renderer renderer;

    private MarkerType(Renderer renderer) {
	this.renderer = renderer;
    }

    @Override
    public void renderTo(Canvas canvas, Rectangle2D location) {
	renderer.renderTo(canvas, location);
    }

    @Override
    public Rectangle2D getLocation() {
	return renderer.getLocation();
    }

}
