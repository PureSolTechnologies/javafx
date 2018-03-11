package com.puresoltechnologies.javafx.charts.renderer;

import com.puresoltechnologies.javafx.charts.plots.Axis;
import com.puresoltechnologies.javafx.charts.plots.ohlc.TimeRange;

import javafx.scene.canvas.Canvas;

public class TimeRangeAxisRenderer extends AbstractAxisRenderer<TimeRange> {

    public TimeRangeAxisRenderer(Canvas canvas, Axis<TimeRange> axis) {
	super(canvas, axis);
    }

}
