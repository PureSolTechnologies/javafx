package com.puresoltechnologies.javafx.charts.plots.box;

import com.puresoltechnologies.javafx.charts.axes.Axis;
import com.puresoltechnologies.javafx.charts.axes.NumberAxis;
import com.puresoltechnologies.javafx.charts.plots.AbstractPlot;
import com.puresoltechnologies.javafx.charts.plots.PlotData;
import com.puresoltechnologies.javafx.charts.renderer.axes.AxisRenderer;
import com.puresoltechnologies.javafx.charts.renderer.axes.NumberAxisRenderer;
import com.puresoltechnologies.javafx.charts.renderer.plots.PlotRenderer;
import com.puresoltechnologies.javafx.charts.renderer.plots.box.BoxPlotRenderer;

import javafx.scene.canvas.Canvas;

public class BoxPlot<X, Y extends Number & Comparable<Y>, D> extends AbstractPlot<X, Y, D> {

    public BoxPlot(String title, Axis<X> xAxis, NumberAxis<Y> yAxis, PlotData<X, Y, D> data) {
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
	return new BoxPlotRenderer<X, Y, D, AxisRenderer<X>>(canvas, this, xAxisRenderer,
		(NumberAxisRenderer) yAxisRenderer);
    }
}
