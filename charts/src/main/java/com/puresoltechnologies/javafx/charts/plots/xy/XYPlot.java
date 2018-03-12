package com.puresoltechnologies.javafx.charts.plots.xy;

import com.puresoltechnologies.javafx.charts.plots.AbstractPlot;
import com.puresoltechnologies.javafx.charts.plots.NumberAxis;
import com.puresoltechnologies.javafx.charts.plots.PlotData;

public class XYPlot<X extends Number & Comparable<X>, Y extends Number & Comparable<Y>, D>
	extends AbstractPlot<X, Y, D> {

    public XYPlot(String title, NumberAxis<X> xAxis, NumberAxis<Y> yAxis, PlotData<X, Y, D> data) {
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
