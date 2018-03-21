package com.puresoltechnologies.javafx.charts.plots.ohlc;

import java.time.Instant;
import java.util.List;

import com.puresoltechnologies.javafx.charts.axes.Axis;
import com.puresoltechnologies.javafx.charts.axes.TimeSeriesAxis;
import com.puresoltechnologies.javafx.charts.plots.AbstractPlot;
import com.puresoltechnologies.javafx.charts.renderer.axes.AxisRenderer;
import com.puresoltechnologies.javafx.charts.renderer.axes.InstantAxisRenderer;
import com.puresoltechnologies.javafx.charts.renderer.axes.NumberAxisRenderer;
import com.puresoltechnologies.javafx.charts.renderer.plots.PlotRenderer;
import com.puresoltechnologies.javafx.charts.renderer.plots.ohlc.OHLCPlotRenderer;

import javafx.scene.canvas.Canvas;

public class OHLCPlot<Y extends Number & Comparable<Y>> extends AbstractPlot<Instant, Y, OHLCValue<Y>> {

    public OHLCPlot(TimeSeriesAxis xAxis, Axis<Y> yAxis) {
	super(xAxis, yAxis);
    }

    public OHLCPlot(String title, TimeSeriesAxis xAxis, Axis<Y> yAxis) {
	super(title, xAxis, yAxis);
    }

    public OHLCPlot(String title, TimeSeriesAxis xAxis, Axis<Y> yAxis, List<OHLCValue<Y>> data) {
	super(title, xAxis, yAxis, data);
    }

    @Override
    protected void updateExtrema() {
	setMinX(null);
	setMaxX(null);
	setMinY(null);
	setMaxY(null);
	getData().forEach(value -> {
	    updateMinX(value.getStart());
	    updateMaxX(value.getEnd());
	    updateMaxY(value.getHigh());
	    updateMinY(value.getLow());
	});
    }

    @Override
    public Instant getAxisX(OHLCValue<Y> date) {
	return date.getEnd();
    }

    @Override
    public Y getAxisY(OHLCValue<Y> date) {
	return date.getClose();
    }

    @Override
    public PlotRenderer getRenderer(Canvas canvas, AxisRenderer<Instant> xAxisRenderer, AxisRenderer<Y> yAxisRenderer) {
	return new OHLCPlotRenderer<>(canvas, this, (InstantAxisRenderer) xAxisRenderer,
		(NumberAxisRenderer) yAxisRenderer);
    }

}
