package com.puresoltechnologies.javafx.charts.plots;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.puresoltechnologies.javafx.charts.AbstractRenderer;
import com.puresoltechnologies.javafx.charts.axes.AxisRenderer;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;

public abstract class AbstractPlotRenderer<X extends Comparable<X>, Y extends Comparable<Y>, D extends PlotDatum<X, Y>, XAR extends AxisRenderer<?>, YAR extends AxisRenderer<?>>
	extends AbstractRenderer implements PlotRenderer<X, Y, D, XAR, YAR> {

    private final Plot<X, Y, D> plot;
    private final XAR xAxisRenderer;
    private final YAR yAxisRenderer;
    private final Map<Rectangle2D, D> plottedPoints = new HashMap<>();

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
    protected void registerPlottedPoint(Rectangle2D location, D dataPoint) {
	plottedPoints.put(location, dataPoint);
    }

    @Override
    public D findDataPoint(double canvasX, double canvasY) {
	D dataPoint = null;
	double minDistance = Double.MAX_VALUE;
	for (Entry<Rectangle2D, D> entry : plottedPoints.entrySet()) {
	    Rectangle2D location = entry.getKey();
	    if (location.contains(canvasX, canvasY)) {
		double centerX = (location.getMaxX() + location.getMinX()) / 2.0;
		double centerY = (location.getMaxY() + location.getMinY()) / 2.0;
		double distance = Math.sqrt( //
			((canvasX - centerX) * (canvasX - centerX)) //
				+ ((canvasY - centerY) * (canvasY - centerY)) //
		);
		if (distance < minDistance) {
		    minDistance = distance;
		    dataPoint = entry.getValue();
		}
	    }
	}
	return dataPoint;
    }

    @Override
    public void renderTo(Canvas canvas, double x, double y, double width, double height) {
	clearPlottedPoints();
    }
}
