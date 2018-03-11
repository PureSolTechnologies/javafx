package com.puresoltechnologies.javafx.charts.plots.ohlc;

import com.puresoltechnologies.javafx.charts.plots.AbstractPlot;
import com.puresoltechnologies.javafx.charts.plots.NumberAxis;
import com.puresoltechnologies.javafx.charts.plots.PlotData;
import com.puresoltechnologies.javafx.charts.plots.TimeRangeAxis;

public class OHLCPlot<Y extends Number> extends AbstractPlot<TimeRange, Y> {

    public OHLCPlot(String title, TimeRangeAxis xAxis, NumberAxis<Y> yAxis, PlotData<TimeRange, Y> data) {
	super(title, xAxis, yAxis, data);
    }

}
