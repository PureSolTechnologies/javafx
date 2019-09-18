package com.puresoltechnologies.javafx.charts.plots.box;

import com.puresoltechnologies.javafx.charts.axes.AxisRenderer;
import com.puresoltechnologies.javafx.charts.axes.NumberAxis;
import com.puresoltechnologies.javafx.charts.axes.NumberAxisRenderer;
import com.puresoltechnologies.javafx.charts.plots.AbstractPlotRenderer;
import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.plots.PlotDatum;

import javafx.scene.canvas.Canvas;

public class BoxPlotRenderer<X extends Comparable<X>, Y extends Number & Comparable<Y>, D extends PlotDatum<X, Y>, XAR extends AxisRenderer<?>>
	extends AbstractPlotRenderer<X, Y, D, XAR, NumberAxisRenderer<Y, NumberAxis<Y>>> {

    public BoxPlotRenderer(Plot<X, Y, D> plot, XAR xAxisRenderer, NumberAxisRenderer yAxisRenderer) {
	super(plot, xAxisRenderer, yAxisRenderer);
    }

    @Override
    public void renderTo(Canvas canvas, double x, double y, double width, double height) {
	super.renderTo(canvas, x, y, width, height);
    }

}
