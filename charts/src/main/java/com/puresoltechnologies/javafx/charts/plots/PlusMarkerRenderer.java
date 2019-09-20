package com.puresoltechnologies.javafx.charts.plots;

import com.puresoltechnologies.javafx.charts.AbstractRenderer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class PlusMarkerRenderer extends AbstractRenderer {

    @Override
    public void draw(Canvas canvas, double x, double y, double width, double height) {
	GraphicsContext gc = canvas.getGraphicsContext2D();
	double size = Math.min(width, height);
	gc.strokeLine(x + (width / 2.0), y + ((height - size) / 2.0), x + (width / 2.0),
		y + ((height - size) / 2.0) + size);
	gc.strokeLine(x + ((width - size) / 2.0), y + (height / 2.0), x + ((width - size) / 2.0) + size,
		y + (height / 2.0));
    }

}
