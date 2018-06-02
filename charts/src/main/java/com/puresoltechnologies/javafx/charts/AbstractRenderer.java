package com.puresoltechnologies.javafx.charts;

import javafx.scene.canvas.Canvas;

public abstract class AbstractRenderer implements Renderer {

    private final Canvas canvas;

    public AbstractRenderer(Canvas canvas) {
	super();
	this.canvas = canvas;
    }

    public final Canvas getCanvas() {
	return canvas;
    }

    public final double calcPosX(double x, double width, double min, double max, double value) {
	return x + width / (max - min) * (value - min);
    }

    public final double calcPosY(double y, double height, double min, double max, double value) {
	return y + height - height / (max - min) * (value - min);
    }
}
