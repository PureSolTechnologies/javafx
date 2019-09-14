package com.puresoltechnologies.javafx.charts.plots;

import com.puresoltechnologies.javafx.charts.Renderer;

import javafx.scene.canvas.Canvas;

/**
 * This enum is used to specify the marker type for a plot.
 *
 * @author Rick-Rainer Ludwig
 */
public enum MarkerType implements Renderer {

    SQUARE(new SquareMarkerRenderer()), //
    CROSS(new CrossMarkerRenderer()), //
    CIRCLE(new CircleMarkerRenderer()), //
    DIAMOND(new DiamondMarkerRenderer()), //
    ;

    private final Renderer renderer;

    private MarkerType(Renderer renderer) {
	this.renderer = renderer;
    }

    @Override
    public void renderTo(Canvas canvas, double x, double y, double width, double height) {
	renderer.renderTo(canvas, x, y, width, height);
    }

}
