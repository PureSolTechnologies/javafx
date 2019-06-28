package com.puresoltechnologies.javafx.charts.plots;

import com.puresoltechnologies.javafx.charts.AbstractRenderer;
import com.puresoltechnologies.javafx.charts.axes.AxisRenderer;

import javafx.scene.canvas.Canvas;

public abstract class AbstractPlotRenderer<X extends Comparable<X>, Y extends Comparable<Y>, D, XAR extends AxisRenderer<?>, YAR extends AxisRenderer<?>>
	extends AbstractRenderer implements PlotRenderer<X, Y, D, XAR, YAR> {

    private final Plot<X, Y, D> plot;
    private final XAR xAxisRenderer;
    private final YAR yAxisRenderer;

    public AbstractPlotRenderer(Canvas canvas, Plot<X, Y, D> plot, XAR xAxisRenderer, YAR yAxisRenderer) {
	super(canvas);
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

}
