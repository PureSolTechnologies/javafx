package com.puresoltechnologies.javafx.charts.plots;

import com.puresoltechnologies.javafx.charts.Renderer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class SquareMarkerRenderer implements Renderer {

    @Override
    public void renderTo(Canvas canvas, double x, double y, double width, double height) {
	GraphicsContext gc = canvas.getGraphicsContext2D();
	double size = Math.min(width, height);
	gc.fillRect(x + ((width - size) / 2.0), y + ((height - size) / 2.0), size, size);
    }

}
