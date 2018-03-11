package com.puresoltechnologies.javafx.charts.plots.xy;

import com.puresoltechnologies.javafx.charts.plots.AbstractPlot;
import com.puresoltechnologies.javafx.charts.plots.NumberAxis;
import com.puresoltechnologies.javafx.charts.plots.PlotData;

public class XYPlot<X extends Number, Y extends Number> extends AbstractPlot<X, Y> {

    public XYPlot(String title, NumberAxis<X> xAxis, NumberAxis<Y> yAxis, PlotData<X, Y> data) {
	super(title, xAxis, yAxis, data);
    }

}
