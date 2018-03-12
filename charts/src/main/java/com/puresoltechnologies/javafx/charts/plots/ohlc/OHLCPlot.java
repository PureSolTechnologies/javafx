package com.puresoltechnologies.javafx.charts.plots.ohlc;

import java.time.Instant;

import com.puresoltechnologies.javafx.charts.plots.AbstractPlot;
import com.puresoltechnologies.javafx.charts.plots.Axis;
import com.puresoltechnologies.javafx.charts.plots.PlotData;
import com.puresoltechnologies.javafx.charts.plots.TimeSeriesAxis;

public class OHLCPlot<Y extends Number & Comparable<Y>> extends AbstractPlot<Instant, Y, OHLCValue<Y>> {

    public OHLCPlot(String title, TimeSeriesAxis xAxis, Axis<Y> yAxis, PlotData<Instant, Y, OHLCValue<Y>> data) {
	super(title, xAxis, yAxis, data);
    }

    @Override
    public Instant getAxisX(OHLCValue<Y> date) {
	return date.getEnd();
    }

    @Override
    public Y getAxisY(OHLCValue<Y> date) {
	return date.getClose();
    }

}
