package com.puresoltechnologies.javafx.charts.plots;

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.puresoltechnologies.javafx.charts.AbstractRenderer;
import com.puresoltechnologies.javafx.charts.axes.AxisRenderer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class AbstractPlotRenderer<X extends Comparable<X>, Y extends Comparable<Y>, D extends PlotDatum<X, Y>, XAR extends AxisRenderer<?>, YAR extends AxisRenderer<?>>
	extends AbstractRenderer implements PlotRenderer<X, Y, D, XAR, YAR> {

    private final DoubleProperty dataPointCaptureRange = new SimpleDoubleProperty(5.0);

    private final Plot<X, Y, D> plot;
    private final XAR xAxisRenderer;
    private final YAR yAxisRenderer;
    private final NavigableMap<Double, NavigableMap<Double, D>> plottedPoints = new TreeMap<>();

    public AbstractPlotRenderer(Plot<X, Y, D> plot, XAR xAxisRenderer, YAR yAxisRenderer) {
	this.plot = plot;
	this.xAxisRenderer = xAxisRenderer;
	this.yAxisRenderer = yAxisRenderer;
    }

    @Override
    public final Plot<X, Y, D> getPlot() {
	return plot;
    }

    @Override
    public final XAR getXAxisRenderer() {
	return xAxisRenderer;
    }

    @Override
    public final YAR getYAxisRenderer() {
	return yAxisRenderer;
    }

    @Override
    public DoubleProperty dataPointCaptureRangeProperty() {
	return dataPointCaptureRange;
    }

    /**
     * Clears all plotted points registered by
     * {@link #registerPlottedPoint(double, double, PlotDatum)}.
     */
    protected void clearPlottedPoints() {
	plottedPoints.clear();
    }

    /**
     *
     * @param x
     * @param y
     * @param dataPoint
     */
    protected void registerPlottedPoint(double x, double y, D dataPoint) {
	NavigableMap<Double, D> yPoints = plottedPoints.get(x);
	if (yPoints == null) {
	    yPoints = new TreeMap<>();
	    plottedPoints.put(x, yPoints);
	}
	yPoints.put(y, dataPoint);
    }

    @Override
    public D findDataPoint(double canvasX, double canvasY) {
	double range = dataPointCaptureRange.get();
	D dataPoint = null;
	double minDistance = Double.MAX_VALUE;
	for (Entry<Double, NavigableMap<Double, D>> xEntry : plottedPoints.subMap(canvasX - range, canvasX + range)
		.entrySet()) {
	    double x = xEntry.getKey();
	    for (Entry<Double, D> yEntry : xEntry.getValue().subMap(canvasY - range, canvasY + range).entrySet()) {
		double y = yEntry.getKey();
		double distance = Math.sqrt(((canvasX - x) * (canvasX - x)) + ((canvasY - y) * (canvasY - y)));
		if ((distance < range) && (distance < minDistance)) {
		    minDistance = distance;
		    dataPoint = yEntry.getValue();
		}
	    }
	}
	return dataPoint;
    }
}
