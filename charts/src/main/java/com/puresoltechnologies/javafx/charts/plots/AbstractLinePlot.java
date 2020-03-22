package com.puresoltechnologies.javafx.charts.plots;

import java.util.List;

import com.puresoltechnologies.javafx.charts.axes.Axis;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class AbstractLinePlot<X extends Comparable<X>, Y extends Comparable<Y>, D extends PlotDatum<X, Y>>
	extends AbstractPointBasedPlot<X, Y, D> implements LinePlot {

    private final DoubleProperty lineWidth = new SimpleDoubleProperty(1.0);

    public AbstractLinePlot(Axis<X> xAxis, Axis<Y> yAxis) {
	super(xAxis, yAxis);
    }

    public AbstractLinePlot(String title, Axis<X> xAxis, Axis<Y> yAxis, List<D> data) {
	super(title, xAxis, yAxis, data);
    }

    public AbstractLinePlot(String title, Axis<X> xAxis, Axis<Y> yAxis) {
	super(title, xAxis, yAxis);
    }

    @Override
    public final DoubleProperty lineWidthProperty() {
	return lineWidth;
    }

}
