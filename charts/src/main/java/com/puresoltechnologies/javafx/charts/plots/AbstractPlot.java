package com.puresoltechnologies.javafx.charts.plots;

import com.puresoltechnologies.javafx.charts.axes.Axis;
import com.puresoltechnologies.javafx.charts.axes.AxisType;
import com.puresoltechnologies.javafx.charts.renderer.axes.AxisRenderer;
import com.puresoltechnologies.javafx.charts.renderer.plots.PlotRenderer;

import javafx.scene.canvas.Canvas;

public abstract class AbstractPlot<X, Y, D> implements Plot<X, Y, D> {

    private final String title;
    private final Axis<X> xAxis;
    private final Axis<Y> yAxis;
    private final PlotData<X, Y, D> data;

    public AbstractPlot(String title, Axis<X> xAxis, Axis<Y> yAxis, PlotData<X, Y, D> data) {
	super();
	this.title = title;
	this.xAxis = xAxis;
	if ((xAxis.getAxisType() != AxisType.X) //
		&& (xAxis.getAxisType() != AxisType.ALT_X)) {
	    throw new IllegalArgumentException("Provided X axis is not an X axis.");
	}
	this.yAxis = yAxis;
	if ((yAxis.getAxisType() != AxisType.Y) //
		&& (yAxis.getAxisType() != AxisType.ALT_Y)) {
	    throw new IllegalArgumentException("Provided Y axis is not an Y axis.");
	}
	this.data = data;
    }

    public abstract PlotRenderer getRenderer(Canvas canvas, AxisRenderer<X> xAxisRenderer,
	    AxisRenderer<Y> yAxisRenderer);

    @SuppressWarnings("unchecked")
    public PlotRenderer getGenericRenderer(PlotArea canvas, AxisRenderer<?> xAxisRenderer,
	    AxisRenderer<?> yAxisRenderer) {
	return getRenderer(canvas, (AxisRenderer<X>) xAxisRenderer, (AxisRenderer<Y>) yAxisRenderer);
    }

    @Override
    public final String getTitle() {
	return title;
    }

    @Override
    public final Axis<X> getXAxis() {
	return xAxis;
    }

    @Override
    public final Axis<Y> getYAxis() {
	return yAxis;
    }

    @Override
    public final PlotData<X, Y, D> getData() {
	return data;
    }

    public abstract X getAxisX(D date);

    public abstract Y getAxisY(D date);

}
