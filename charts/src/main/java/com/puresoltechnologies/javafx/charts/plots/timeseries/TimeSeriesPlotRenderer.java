package com.puresoltechnologies.javafx.charts.plots.timeseries;

import java.time.Instant;
import java.util.List;

import com.puresoltechnologies.javafx.charts.axes.InstantAxisRenderer;
import com.puresoltechnologies.javafx.charts.axes.NumberAxisRenderer;
import com.puresoltechnologies.javafx.charts.plots.AbstractPlotRenderer;
import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TimeSeriesPlotRenderer<Y extends Number & Comparable<Y>, D>
	extends AbstractPlotRenderer<Instant, Y, D, InstantAxisRenderer, NumberAxisRenderer> {

    public TimeSeriesPlotRenderer(Plot<Instant, Y, D> plot, InstantAxisRenderer xAxisRenderer,
	    NumberAxisRenderer yAxisRenderer) {
	super(plot, xAxisRenderer, yAxisRenderer);
    }

    @Override
    public void renderTo(Canvas canvas, double x, double y, double width, double height) {
	TimeSeriesPlot<Y, D> plot = (TimeSeriesPlot<Y, D>) getPlot();
	List<D> data = plot.getData();
	if (data.size() == 0) {
	    return;
	}
	InstantAxisRenderer xAxisRenderer = getXAxisRenderer();
	NumberAxisRenderer yAxisRenderer = getYAxisRenderer();
	GraphicsContext gc = canvas.getGraphicsContext2D();
	gc.setStroke(Color.BLACK);
	gc.setLineWidth(1.0);
	double lastXValue = xAxisRenderer.calculatePos(x, y, width, height, plot.getAxisX(data.get(0)));
	double lastYValue = yAxisRenderer.calculatePos(x, y, width, height, plot.getAxisY(data.get(0)));
	for (D value : data) {
	    double xValue = xAxisRenderer.calculatePos(x, y, width, height, plot.getAxisX(value));
	    double yValue = yAxisRenderer.calculatePos(x, y, width, height, plot.getAxisY(value));
	    if ((!Double.isNaN(xValue)) && (!Double.isNaN(yValue))) {
		gc.setStroke(Color.BLACK);
		gc.strokeLine(lastXValue, lastYValue, xValue, yValue);
	    }
	    lastXValue = xValue;
	    lastYValue = yValue;
	}
    }

}
