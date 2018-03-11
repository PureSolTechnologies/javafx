package com.puresoltechnologies.javafx.charts.plots.box;

import com.puresoltechnologies.javafx.charts.plots.AbstractPlot;
import com.puresoltechnologies.javafx.charts.plots.Axis;
import com.puresoltechnologies.javafx.charts.plots.OrdinalAxis;
import com.puresoltechnologies.javafx.charts.plots.PlotData;

public class BoxPlot<X, Y extends Comparable<Y>> extends AbstractPlot<X, Y> {

    public BoxPlot(String title, Axis<X> xAxis, OrdinalAxis<Y> yAxis, PlotData<X, Y> data) {
	super(title, xAxis, yAxis, data);
    }

}
