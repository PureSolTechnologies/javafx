package com.puresoltechnologies.javafx.charts.plots;

import java.time.Instant;

public class TimeSeriesAxis extends AbstractAxis<Instant> {

    public TimeSeriesAxis(String title, AxisType type) {
	super(title, null, type, Instant.class);
    }

}