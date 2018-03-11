package com.puresoltechnologies.javafx.charts.renderer;

import java.time.Instant;

import com.puresoltechnologies.javafx.charts.plots.Axis;
import com.puresoltechnologies.javafx.charts.plots.ohlc.TimeRange;

import javafx.scene.canvas.Canvas;

public class AxisRendererFactory {

    @SuppressWarnings("unchecked")
    public static AxisRenderer forAxis(Canvas canvas, Axis<?> axis) {
	Class<?> valueType = axis.getValueType();
	if (Instant.class.isAssignableFrom(valueType)) {
	    return new InstantAxisRenderer(canvas, (Axis<Instant>) axis);
	} else if (Number.class.isAssignableFrom(valueType)) {
	    return new NumberAxisRenderer(canvas, (Axis<Number>) axis);
	} else if (TimeRange.class.isAssignableFrom(valueType)) {
	    return new TimeRangeAxisRenderer(canvas, (Axis<TimeRange>) axis);
	} else {
	    return new NominalAxisRenderer(canvas, (Axis<Object>) axis);
	}
    }

}
