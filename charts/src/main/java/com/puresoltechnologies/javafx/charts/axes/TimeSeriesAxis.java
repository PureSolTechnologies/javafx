package com.puresoltechnologies.javafx.charts.axes;

import java.time.Instant;

public class TimeSeriesAxis extends OrdinalAxis<Instant> {

    public TimeSeriesAxis(String title, AxisType type) {
	super(title, null, type, Instant.class);
    }

}
