package com.puresoltechnologies.javafx.charts.plots;

import com.puresoltechnologies.javafx.charts.Renderer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class DiamondMarkerRenderer implements Renderer {

    @Override
    public void renderTo(Canvas canvas, double x, double y, double width, double height) {
	GraphicsContext gc = canvas.getGraphicsContext2D();
	double size = Math.min(width, height);
	double centerX = x + (width / 2.0);
	double centerY = y + (height / 2.0);
	gc.fillPolygon(new double[] { centerX, centerX + (size / 2.0), centerX, centerX - (size / 2.0) }, //
		new double[] { centerY - (size / 2.0), centerY, centerY + (size / 2.0), centerY }, //
		4);
    }

}
