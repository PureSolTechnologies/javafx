package com.puresoltechnologies.javafx.charts.renderer.axis;

import java.time.Instant;
import java.util.List;

import com.puresoltechnologies.javafx.charts.plots.Axis;
import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.scene.canvas.Canvas;

public class AxisRendererFactory {

    @SuppressWarnings("unchecked")
    public static AxisRenderer forAxis(Canvas canvas, Axis<?> axis, List<Plot<?, ?, ?>> plots) {
	Class<?> valueType = axis.getValueType();
	if (Instant.class.isAssignableFrom(valueType)) {
	    return new InstantAxisRenderer(canvas, (Axis<Instant>) axis, plots);
	} else if (Number.class.isAssignableFrom(valueType)) {
	    return new NumberAxisRenderer(canvas, (Axis<Number>) axis, plots);
	} else if (Comparable.class.isAssignableFrom(valueType)) {
	    return new OridinalAxisRenderer(canvas, (Axis<Comparable<Object>>) axis, plots);
	} else {
	    return new NominalAxisRenderer(canvas, (Axis<Object>) axis, plots);
	}
    }

}
