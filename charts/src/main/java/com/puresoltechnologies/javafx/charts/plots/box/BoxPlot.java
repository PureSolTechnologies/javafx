package com.puresoltechnologies.javafx.charts.plots.box;

import com.puresoltechnologies.javafx.charts.plots.AbstractPlot;
import com.puresoltechnologies.javafx.charts.plots.Axis;
import com.puresoltechnologies.javafx.charts.plots.OrdinalAxis;
import com.puresoltechnologies.javafx.charts.plots.PlotData;

public class BoxPlot<X, Y extends Comparable<Y>, D> extends AbstractPlot<X, Y, D> {

    public BoxPlot(String title, Axis<X> xAxis, OrdinalAxis<Y> yAxis, PlotData<X, Y, D> data) {
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

}
