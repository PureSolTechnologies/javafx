package com.puresoltechnologies.javafx.charts.plots.xy;

import java.util.List;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import com.puresoltechnologies.javafx.charts.axes.NumberAxis;
import com.puresoltechnologies.javafx.charts.axes.NumberAxisRenderer;
import com.puresoltechnologies.javafx.charts.plots.AbstractPlotRenderer;
import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.plots.PlotDatum;

import javafx.geometry.Rectangle2D;
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
    public void draw(Canvas canvas, double x, double y, double width, double height) {
	super.draw(canvas, x, y, width, height);
	if ((width <= 0) || (height <= 0)) {
	    return;
	}
	drawInterpolation(canvas, x, y, width, height);
	drawPoints(canvas, x, y, width, height);
    }

    private void drawPoints(Canvas canvas, double x, double y, double width, double height) {
	@SuppressWarnings("unchecked")
	XYPlot<X, Y> plot = (XYPlot<X, Y>) getPlot();
	List<XYValue<X, Y>> data = plot.getData();
	if (data.size() == 0) {
	    return;
	}
	GraphicsContext gc = canvas.getGraphicsContext2D();
	NumberAxisRenderer<?, ?> xAxisRenderer = getXAxisRenderer();
	NumberAxisRenderer<?, ?> yAxisRenderer = getYAxisRenderer();
	gc.setStroke(plot.getColor());
	gc.setFill(plot.getColor());
	gc.setLineWidth(plot.getLineWidth());

	double markerSize = plot.markerSizeProperty().get();
	for (XYValue<X, Y> value : data) {
	    double posX = xAxisRenderer.calculatePos(x, y, width, height, value.getX());
	    double posY = yAxisRenderer.calculatePos(x, y, width, height, value.getY());
	    double renderX = posX - (markerSize / 2.0);
	    double renderY = posY - (markerSize / 2.0);
	    plot.getMarkerType().renderTo(canvas, renderX, renderY, markerSize, markerSize);
	    @SuppressWarnings("unchecked")
	    D d = (D) value;
	    registerPlottedPoint(new Rectangle2D(renderX, renderY, markerSize, markerSize), d);
	}
    }

    private void drawInterpolation(Canvas canvas, double x, double y, double width, double height) {
	@SuppressWarnings("unchecked")
	XYPlot<X, Y> plot = (XYPlot<X, Y>) getPlot();
	switch (plot.getInterpolationType()) {
	case NONE:
	    // Nothing to draw
	    return;
	case STRAIGHT_LINE:
	    drawStraightInterpolation(canvas, x, y, width, height);
	    return;
	case CUBIC_SPLINES:
	    drawCubicInterpolation(canvas, x, y, width, height);
	    return;
	}
    }

    private void drawStraightInterpolation(Canvas canvas, double x, double y, double width, double height) {
	GraphicsContext gc = canvas.getGraphicsContext2D();
	NumberAxisRenderer<?, ?> xAxisRenderer = getXAxisRenderer();
	NumberAxisRenderer<?, ?> yAxisRenderer = getYAxisRenderer();
	@SuppressWarnings("unchecked")
	XYPlot<X, Y> plot = (XYPlot<X, Y>) getPlot();
	gc.setStroke(plot.getLineColor());
	gc.setFill(plot.getLineColor());
	gc.setLineWidth(plot.getLineWidth());
	double oldPosX = -Double.MAX_VALUE;
	double oldPosY = -Double.MAX_VALUE;
	for (XYValue<X, Y> value : plot.getData()) {
	    double posX = xAxisRenderer.calculatePos(x, y, width, height, value.getX());
	    double posY = yAxisRenderer.calculatePos(x, y, width, height, value.getY());
	    if (oldPosX != -Double.MAX_VALUE) {
		gc.setGlobalAlpha(plot.getLineAlpha());
		gc.strokeLine(oldPosX, oldPosY, posX, posY);
		gc.setGlobalAlpha(1.0);
	    }
	    oldPosX = posX;
	    oldPosY = posY;
	}

    }

    private void drawCubicInterpolation(Canvas canvas, double x, double y, double width, double height) {
	NumberAxisRenderer<?, ?> xAxisRenderer = getXAxisRenderer();
	NumberAxisRenderer<?, ?> yAxisRenderer = getYAxisRenderer();
	@SuppressWarnings("unchecked")
	XYPlot<X, Y> plot = (XYPlot<X, Y>) getPlot();

	List<XYValue<X, Y>> data = plot.getData();
	double xValues[] = new double[data.size()];
	double yValues[] = new double[data.size()];
	for (int i = 0; i < data.size(); i++) {
	    XYValue<X, Y> value = data.get(i);
	    xValues[i] = xAxisRenderer.calculatePos(x, y, width, height, value.getX());
	    yValues[i] = yAxisRenderer.calculatePos(x, y, width, height, value.getY());
	}
	PolynomialSplineFunction interpolator = new SplineInterpolator().interpolate(xValues, yValues);

	GraphicsContext gc = canvas.getGraphicsContext2D();
	gc.setStroke(plot.getLineColor());
	gc.setFill(plot.getLineColor());
	gc.setLineWidth(plot.getLineWidth());
	double oldPosX = -Double.MAX_VALUE;
	double oldPosY = -Double.MAX_VALUE;
	for (double posX = xValues[0]; posX <= xValues[xValues.length - 1]; posX += 1.0) {
	    double posY = interpolator.value(posX);
	    if (oldPosX != -Double.MAX_VALUE) {
		gc.setGlobalAlpha(0.2);
		gc.strokeLine(oldPosX, oldPosY, posX, posY);
		gc.setGlobalAlpha(1.0);
	    }
	    oldPosX = posX;
	    oldPosY = posY;
	}
    }
}
