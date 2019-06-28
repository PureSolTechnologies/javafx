package com.puresoltechnologies.javafx.charts.plots.xy;

import com.puresoltechnologies.javafx.charts.axes.NumberAxisRenderer;
import com.puresoltechnologies.javafx.charts.plots.AbstractPlotRenderer;
import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class XYPlotRenderer<X extends Number & Comparable<X>, Y extends Number & Comparable<Y>, D>
	extends AbstractPlotRenderer<X, Y, D, NumberAxisRenderer, NumberAxisRenderer> {

    public XYPlotRenderer(Canvas canvas, Plot<X, Y, D> plot, NumberAxisRenderer xAxisRenderer,
	    NumberAxisRenderer yAxisRenderer) {
	super(canvas, plot, xAxisRenderer, yAxisRenderer);
    }

    @Override
    public void renderTo(double x, double y, double width, double height) {
	GraphicsContext gc = getCanvas().getGraphicsContext2D();
	NumberAxisRenderer xAxisRenderer = getXAxisRenderer();
	NumberAxisRenderer yAxisRenderer = getYAxisRenderer();
	@SuppressWarnings("unchecked")
	XYPlot<X, Y> plot = (XYPlot<X, Y>) getPlot();
	gc.setStroke(Color.BLACK);
	gc.setLineWidth(1.0);
	for (XYValue<X, Y> value : plot.getData()) {
	    double posX = xAxisRenderer.calculatePos(x, y, width, height, value.getX());
	    double posY = yAxisRenderer.calculatePos(x, y, width, height, value.getY());
	    gc.setFill(Color.GRAY);
	    gc.fillRect(posX - 3.0, posY - 3.0, 6.0, 6.0);
	}
    }

}
