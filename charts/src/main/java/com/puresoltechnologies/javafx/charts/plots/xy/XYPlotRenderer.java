package com.puresoltechnologies.javafx.charts.plots.xy;

import com.puresoltechnologies.javafx.charts.axes.NumberAxis;
import com.puresoltechnologies.javafx.charts.axes.NumberAxisRenderer;
import com.puresoltechnologies.javafx.charts.plots.AbstractPlotRenderer;
import com.puresoltechnologies.javafx.charts.plots.InterpolationType;
import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.plots.PlotDatum;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class XYPlotRenderer<X extends Number & Comparable<X>, Y extends Number & Comparable<Y>, D extends PlotDatum<X, Y>>
	extends
	AbstractPlotRenderer<X, Y, D, NumberAxisRenderer<X, NumberAxis<X>>, NumberAxisRenderer<Y, NumberAxis<Y>>> {

    public XYPlotRenderer(Plot<X, Y, D> plot, NumberAxisRenderer<X, NumberAxis<X>> xAxisRenderer,
	    NumberAxisRenderer<Y, NumberAxis<Y>> yAxisRenderer) {
	super(plot, xAxisRenderer, yAxisRenderer);
    }

    @Override
    public void renderTo(Canvas canvas, double x, double y, double width, double height) {
	GraphicsContext gc = canvas.getGraphicsContext2D();
	NumberAxisRenderer<?, ?> xAxisRenderer = getXAxisRenderer();
	NumberAxisRenderer<?, ?> yAxisRenderer = getYAxisRenderer();
	@SuppressWarnings("unchecked")
	XYPlot<X, Y> plot = (XYPlot<X, Y>) getPlot();
	gc.setStroke(plot.getColor());
	gc.setFill(plot.getColor());
	gc.setLineWidth(1.0);
	double oldPosX = -Double.MAX_VALUE;
	double oldPosY = -Double.MAX_VALUE;
	double markerSize = plot.markerSizeProperty().get();
	for (XYValue<X, Y> value : plot.getData()) {
	    double posX = xAxisRenderer.calculatePos(x, y, width, height, value.getX());
	    double posY = yAxisRenderer.calculatePos(x, y, width, height, value.getY());
	    plot.getMarkerType().renderTo(canvas, posX - (markerSize / 2.0), posY - (markerSize / 2.0), markerSize,
		    markerSize);
	    if (posX != -Double.MAX_VALUE) {
		if (plot.getInterpolationType() == InterpolationType.STRAIGHT_LINE) {
		    gc.setGlobalAlpha(0.2);
		    gc.strokeLine(oldPosX, oldPosY, posX, posY);
		    gc.setGlobalAlpha(1.0);
		}
	    }
	    oldPosX = posX;
	    oldPosY = posY;
	}
    }

}
