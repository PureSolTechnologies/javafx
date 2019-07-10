package com.puresoltechnologies.javafx.charts.plots.box;

import java.util.List;

import com.puresoltechnologies.javafx.charts.axes.Axis;
import com.puresoltechnologies.javafx.charts.axes.AxisRenderer;
import com.puresoltechnologies.javafx.charts.axes.NumberAxis;
import com.puresoltechnologies.javafx.charts.axes.NumberAxisRenderer;
import com.puresoltechnologies.javafx.charts.plots.AbstractPlot;
import com.puresoltechnologies.javafx.charts.plots.PlotRenderer;

public class BoxPlot<X extends Comparable<X>, Y extends Number & Comparable<Y>, D> extends AbstractPlot<X, Y, D> {

    public BoxPlot(String title, Axis<X> xAxis, NumberAxis<Y> yAxis, List<D> data) {
	super(title, xAxis, yAxis, data);
    }

    @Override
    public X getAxisX(D date) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Y getAxisY(D date) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PlotRenderer getRenderer(AxisRenderer<X> xAxisRenderer, AxisRenderer<Y> yAxisRenderer) {
	return new BoxPlotRenderer<>(this, xAxisRenderer, (NumberAxisRenderer) yAxisRenderer);
    }
}
