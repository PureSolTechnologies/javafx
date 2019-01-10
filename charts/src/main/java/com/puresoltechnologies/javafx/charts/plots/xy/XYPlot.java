package com.puresoltechnologies.javafx.charts.plots.xy;

import java.util.List;

import com.puresoltechnologies.javafx.charts.axes.AxisRenderer;
import com.puresoltechnologies.javafx.charts.axes.NumberAxis;
import com.puresoltechnologies.javafx.charts.axes.NumberAxisRenderer;
import com.puresoltechnologies.javafx.charts.plots.AbstractPlot;
import com.puresoltechnologies.javafx.charts.plots.PlotRenderer;

import javafx.scene.canvas.Canvas;

public class XYPlot<X extends Number & Comparable<X>, Y extends Number & Comparable<Y>, D>
	extends AbstractPlot<X, Y, D> {

    public XYPlot(String title, NumberAxis<X> xAxis, NumberAxis<Y> yAxis) {
	super(title, xAxis, yAxis);
    }

    public XYPlot(String title, NumberAxis<X> xAxis, NumberAxis<Y> yAxis, List<D> data) {
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
    public PlotRenderer getRenderer(Canvas canvas, AxisRenderer<X> xAxisRenderer, AxisRenderer<Y> yAxisRenderer) {
	return new XYPlotRenderer<>(canvas, this, (NumberAxisRenderer) xAxisRenderer,
		(NumberAxisRenderer) yAxisRenderer);
    }

}
