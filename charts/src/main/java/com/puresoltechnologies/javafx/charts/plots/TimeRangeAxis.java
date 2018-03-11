package com.puresoltechnologies.javafx.charts.plots;

import com.puresoltechnologies.javafx.charts.plots.ohlc.TimeRange;

public class TimeRangeAxis extends AbstractAxis<TimeRange> {

    public TimeRangeAxis(String title, String unit, AxisType type) {
	super(title, unit, type, TimeRange.class);
    }

}
