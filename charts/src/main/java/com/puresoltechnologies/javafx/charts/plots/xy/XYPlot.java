package com.puresoltechnologies.javafx.charts.plots.xy;

import java.util.List;

import com.puresoltechnologies.javafx.charts.axes.AxisRenderer;
import com.puresoltechnologies.javafx.charts.axes.NumberAxis;
import com.puresoltechnologies.javafx.charts.axes.NumberAxisRenderer;
import com.puresoltechnologies.javafx.charts.plots.AbstractPointBasedPlot;
import com.puresoltechnologies.javafx.charts.plots.PlotRenderer;

public class XYPlot<X extends Number & Comparable<X>, Y extends Number & Comparable<Y>>
	extends AbstractPointBasedPlot<X, Y, XYValue<X, Y>> {

    public XYPlot(String title, NumberAxis<X> xAxis, NumberAxis<Y> yAxis) {
	super(title, xAxis, yAxis);
    }

    public XYPlot(String title, NumberAxis<X> xAxis, NumberAxis<Y> yAxis, List<XYValue<X, Y>> data) {
	super(title, xAxis, yAxis, data);
    }

    @Override
    public X getAxisX(XYValue<X, Y> date) {
	return date.getX();
    }

    @Override
    public Y getAxisY(XYValue<X, Y> date) {
	return date.getY();
    }

    @Override
    public PlotRenderer<X, Y, XYValue<X, Y>, ? extends AxisRenderer<X>, ? extends AxisRenderer<Y>> getRenderer(
	    AxisRenderer<X> xAxisRenderer, AxisRenderer<Y> yAxisRenderer) {
	@SuppressWarnings("unchecked")
	PlotRenderer<X, Y, XYValue<X, Y>, ? extends AxisRenderer<X>, ? extends AxisRenderer<Y>> renderer = (PlotRenderer<X, Y, XYValue<X, Y>, ? extends AxisRenderer<X>, ? extends AxisRenderer<Y>>) new XYPlotRenderer<>(
		this, (NumberAxisRenderer) xAxisRenderer, (NumberAxisRenderer) yAxisRenderer);
	return renderer;
    }
}
