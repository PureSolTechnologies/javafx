package com.puresoltechnologies.javafx.charts.plots;

import com.puresoltechnologies.javafx.charts.AbstractRenderer;
import com.puresoltechnologies.javafx.charts.axes.AxisRenderer;

import javafx.scene.canvas.Canvas;

public abstract class AbstractPlotRenderer<X extends Comparable<X>, Y extends Comparable<Y>, D, XAR extends AxisRenderer<?>, YAR extends AxisRenderer<?>>
	extends AbstractRenderer implements PlotRenderer {

    private final Plot<X, Y, D> plot;
    private final XAR xAxisRenderer;
    private final YAR yAxisRenderer;

    public AbstractPlotRenderer(Canvas canvas, Plot<X, Y, D> plot, XAR xAxisRenderer, YAR yAxisRenderer) {
	super(canvas);
	this.plot = plot;
	this.xAxisRenderer = xAxisRenderer;
	this.yAxisRenderer = yAxisRenderer;
    }

    public final Plot<X, Y, D> getPlot() {
	return plot;
    }

    public final XAR getXAxisRenderer() {
	return xAxisRenderer;
    }

    public final YAR getYAxisRenderer() {
	return yAxisRenderer;
    }

}
