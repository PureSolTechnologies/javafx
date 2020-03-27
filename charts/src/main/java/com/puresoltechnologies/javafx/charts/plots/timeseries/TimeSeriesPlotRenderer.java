package com.puresoltechnologies.javafx.charts.plots.timeseries;

import java.time.Instant;
import java.util.List;

import com.puresoltechnologies.javafx.charts.axes.InstantAxisRenderer;
import com.puresoltechnologies.javafx.charts.axes.NumberAxis;
import com.puresoltechnologies.javafx.charts.axes.NumberAxisRenderer;
import com.puresoltechnologies.javafx.charts.axes.OrdinalAxis;
import com.puresoltechnologies.javafx.charts.plots.AbstractPlotRenderer;
import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.plots.PlotDatum;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class TimeSeriesPlotRenderer<Y extends Number & Comparable<Y>, D extends PlotDatum<Instant, Y>> extends
	AbstractPlotRenderer<Instant, Y, D, InstantAxisRenderer<OrdinalAxis<Instant>>, NumberAxisRenderer<Y, NumberAxis<Y>>> {

    public TimeSeriesPlotRenderer(Plot<Instant, Y, D> plot, InstantAxisRenderer<OrdinalAxis<Instant>> xAxisRenderer,
	    NumberAxisRenderer<Y, NumberAxis<Y>> yAxisRenderer) {
	super(plot, xAxisRenderer, yAxisRenderer);
    }

    @Override
    public void draw(Canvas canvas, double x, double y, double width, double height) {
	super.draw(canvas, x, y, width, height);
	if ((width <= 0) || (height <= 0)) {
	    return;
	}
	drawPoints(canvas, x, y, width, height);
	drawLines(canvas, x, y, width, height);
    }

    private void drawPoints(Canvas canvas, double x, double y, double width, double height) {
	TimeSeriesPlot<Y, D> plot = (TimeSeriesPlot<Y, D>) getPlot();
	List<D> data = plot.getData();
	if (data.size() == 0) {
	    return;
	}
	GraphicsContext gc = canvas.getGraphicsContext2D();
	InstantAxisRenderer<OrdinalAxis<Instant>> xAxisRenderer = getXAxisRenderer();
	NumberAxisRenderer<?, ?> yAxisRenderer = getYAxisRenderer();
	gc.setStroke(plot.getColor());
	gc.setFill(plot.getColor());
	gc.setLineWidth(plot.getLineWidth());

	double markerSize = plot.markerSizeProperty().get();
	for (D value : data) {
	    double posX = xAxisRenderer.calculatePos(x, y, width, height, plot.getAxisX(value));
	    double posY = yAxisRenderer.calculatePos(x, y, width, height, plot.getAxisY(value));
	    double renderX = posX - (markerSize / 2.0);
	    double renderY = posY - (markerSize / 2.0);
	    plot.getMarkerType().renderTo(canvas, renderX, renderY, markerSize, markerSize);
	    registerPlottedPoint(new Rectangle2D(renderX, renderY, markerSize, markerSize), value);
	}
    }

    private void drawLines(Canvas canvas, double x, double y, double width, double height) {
	TimeSeriesPlot<Y, D> plot = (TimeSeriesPlot<Y, D>) getPlot();
	List<D> data = plot.getData();
	if (data.size() == 0) {
	    return;
	}
	GraphicsContext gc = canvas.getGraphicsContext2D();
	InstantAxisRenderer<?> xAxisRenderer = getXAxisRenderer();
	NumberAxisRenderer<?, ?> yAxisRenderer = getYAxisRenderer();
	gc.setStroke(plot.getColor());
	gc.setLineWidth(plot.getLineWidth());
	double lastXValue = xAxisRenderer.calculatePos(x, y, width, height, plot.getAxisX(data.get(0)));
	double lastYValue = yAxisRenderer.calculatePos(x, y, width, height, plot.getAxisY(data.get(0)));
	for (D value : data) {
	    double xValue = xAxisRenderer.calculatePos(x, y, width, height, plot.getAxisX(value));
	    double yValue = yAxisRenderer.calculatePos(x, y, width, height, plot.getAxisY(value));
	    if ((!Double.isNaN(xValue)) && (!Double.isNaN(yValue))) {
		gc.setGlobalAlpha(plot.getLineAlpha());
		gc.strokeLine(lastXValue, lastYValue, xValue, yValue);
		gc.setGlobalAlpha(1.0);
	    }
	    lastXValue = xValue;
	    lastYValue = yValue;
	}
    }

}
