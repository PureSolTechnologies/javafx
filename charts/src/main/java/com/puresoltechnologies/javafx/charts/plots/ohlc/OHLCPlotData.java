package com.puresoltechnologies.javafx.charts.plots.ohlc;

import java.time.Instant;

import com.puresoltechnologies.javafx.charts.plots.AbstractPlotData;

public class OHLCPlotData<Y extends Number & Comparable<Y>> extends AbstractPlotData<Instant, Y, OHLCValue<Y>> {

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

}
