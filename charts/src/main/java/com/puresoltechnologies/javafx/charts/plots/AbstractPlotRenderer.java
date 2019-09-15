package com.puresoltechnologies.javafx.charts.plots;

import com.puresoltechnologies.javafx.charts.AbstractRenderer;
import com.puresoltechnologies.javafx.charts.axes.AxisRenderer;

public abstract class AbstractPlotRenderer<X extends Comparable<X>, Y extends Comparable<Y>, D extends PlotDatum<X, Y>, XAR extends AxisRenderer<?>, YAR extends AxisRenderer<?>>
	extends AbstractRenderer implements PlotRenderer<X, Y, D, XAR, YAR> {

    private final Plot<X, Y, D> plot;
    private final XAR xAxisRenderer;
    private final YAR yAxisRenderer;

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

}
