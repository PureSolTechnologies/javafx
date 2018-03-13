package com.puresoltechnologies.javafx.charts.renderer.plots.box;

import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.renderer.axes.AxisRenderer;
import com.puresoltechnologies.javafx.charts.renderer.axes.NumberAxisRenderer;
import com.puresoltechnologies.javafx.charts.renderer.plots.AbstractPlotRenderer;

import javafx.scene.canvas.Canvas;

public class BoxPlotRenderer<X, Y extends Number & Comparable<Y>, D, XAR extends AxisRenderer<?>>
	extends AbstractPlotRenderer<X, Y, D, XAR, NumberAxisRenderer> {

    public BoxPlotRenderer(Canvas canvas, Plot<X, Y, D> plot, XAR xAxisRenderer, NumberAxisRenderer yAxisRenderer) {
	super(canvas, plot, xAxisRenderer, yAxisRenderer);
    }

    @Override
    public void renderTo(double x, double y, double width, double height) {
	// TODO Auto-generated method stub

    }

}
