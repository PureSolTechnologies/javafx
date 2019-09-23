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
	    return new InstantAxisRenderer<>((TimeSeriesAxis) axis, plots);
	} else if (Double.class.isAssignableFrom(valueType)) {
	    return new NumberAxisRenderer<>((NumberAxis<Double>) axis, plots);
	} else if (Float.class.isAssignableFrom(valueType)) {
	    return new NumberAxisRenderer<>((NumberAxis<Float>) axis, plots);
	} else if (Byte.class.isAssignableFrom(valueType)) {
	    return new NumberAxisRenderer<>((NumberAxis<Byte>) axis, plots);
	} else if (Short.class.isAssignableFrom(valueType)) {
	    return new NumberAxisRenderer<>((NumberAxis<Short>) axis, plots);
	} else if (Integer.class.isAssignableFrom(valueType)) {
	    return new NumberAxisRenderer<>((NumberAxis<Integer>) axis, plots);
	} else if (Long.class.isAssignableFrom(valueType)) {
	    return new NumberAxisRenderer<>((NumberAxis<Long>) axis, plots);
	} else if (Comparable.class.isAssignableFrom(valueType)) {
	    return new OrdinalAxisRenderer(axis, plots);
	} else {
	    return new NominalAxisRenderer(axis, plots);
	}
    }

}
