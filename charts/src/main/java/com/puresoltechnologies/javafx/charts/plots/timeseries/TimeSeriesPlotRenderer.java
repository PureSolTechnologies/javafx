package com.puresoltechnologies.javafx.charts.plots.timeseries;

import java.time.Instant;
import java.util.List;

import com.puresoltechnologies.javafx.charts.axes.Axis;
import com.puresoltechnologies.javafx.charts.axes.InstantAxisRenderer;
import com.puresoltechnologies.javafx.charts.axes.NumberAxis;
import com.puresoltechnologies.javafx.charts.axes.NumberAxisRenderer;
import com.puresoltechnologies.javafx.charts.plots.AbstractPlotRenderer;
import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.plots.PlotDatum;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class TimeSeriesPlotRenderer<Y extends Number & Comparable<Y>, D extends PlotDatum<Instant, Y>> extends
	AbstractPlotRenderer<Instant, Y, D, InstantAxisRenderer<Axis<Instant>>, NumberAxisRenderer<Y, NumberAxis<Y>>> {

    public TimeSeriesPlotRenderer(Plot<Instant, Y, D> plot, InstantAxisRenderer<Axis<Instant>> xAxisRenderer,
	    NumberAxisRenderer<Y, NumberAxis<Y>> yAxisRenderer) {
	super(plot, xAxisRenderer, yAxisRenderer);
    }

    @Override
    public void renderTo(Canvas canvas, double x, double y, double width, double height) {
	super.renderTo(canvas, x, y, width, height);
	if ((width <= 0) || (height <= 0)) {
	    return;
	}
	drawPoints(canvas, x, y, width, height);
    }

    private void drawPoints(Canvas canvas, double x, double y, double width, double height) {
	TimeSeriesPlot<Y, D> plot = (TimeSeriesPlot<Y, D>) getPlot();
	List<D> data = plot.getData();
	if (data.size() == 0) {
	    return;
	}
	GraphicsContext gc = canvas.getGraphicsContext2D();
	InstantAxisRenderer<?> xAxisRenderer = getXAxisRenderer();
	NumberAxisRenderer<?, ?> yAxisRenderer = getYAxisRenderer();
	gc.setStroke(plot.getColor());
	gc.setLineWidth(1.0);
	double lastXValue = xAxisRenderer.calculatePos(x, y, width, height, plot.getAxisX(data.get(0)));
	double lastYValue = yAxisRenderer.calculatePos(x, y, width, height, plot.getAxisY(data.get(0)));
	for (D value : data) {
	    double xValue = xAxisRenderer.calculatePos(x, y, width, height, plot.getAxisX(value));
	    double yValue = yAxisRenderer.calculatePos(x, y, width, height, plot.getAxisY(value));
	    if ((!Double.isNaN(xValue)) && (!Double.isNaN(yValue))) {
		gc.strokeLine(lastXValue, lastYValue, xValue, yValue);
	    }
	    lastXValue = xValue;
	    lastYValue = yValue;
	}
    }

}
