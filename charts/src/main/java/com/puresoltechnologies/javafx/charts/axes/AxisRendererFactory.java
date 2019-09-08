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
	    return new InstantAxisRenderer(axis, plots);
	} else if (Double.class.isAssignableFrom(valueType)) {
	    return new NumberAxisRenderer((NumberAxis<Double>) axis, plots);
	} else if (Float.class.isAssignableFrom(valueType)) {
	    return new NumberAxisRenderer((NumberAxis<Float>) axis, plots);
	} else if (Comparable.class.isAssignableFrom(valueType)) {
	    return new OridinalAxisRenderer(axis, plots);
	} else {
	    return new NominalAxisRenderer(axis, plots);
	}
    }

}
