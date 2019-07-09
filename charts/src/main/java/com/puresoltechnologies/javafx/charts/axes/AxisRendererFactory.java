package com.puresoltechnologies.javafx.charts.axes;

import java.time.Instant;

import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;

public class AxisRendererFactory {

    @SuppressWarnings("unchecked")
    public static AxisRenderer<?> forAxis(Canvas canvas, Axis<?> axis, ObservableList<Plot<?, ?, ?>> plots) {
	Class<?> valueType = axis.getValueType();
	if (Instant.class.isAssignableFrom(valueType)) {
	    return new InstantAxisRenderer((Axis<Instant>) axis, plots);
	} else if (Number.class.isAssignableFrom(valueType)) {
	    return new NumberAxisRenderer((Axis<Number>) axis, plots);
	} else if (Comparable.class.isAssignableFrom(valueType)) {
	    return new OridinalAxisRenderer((Axis<Comparable<Object>>) axis, plots);
	} else {
	    return new NominalAxisRenderer((Axis<Object>) axis, plots);
	}
    }

}
