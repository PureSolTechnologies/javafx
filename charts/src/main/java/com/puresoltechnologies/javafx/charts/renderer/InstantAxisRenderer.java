package com.puresoltechnologies.javafx.charts.renderer;

import java.time.Instant;

import com.puresoltechnologies.javafx.charts.plots.Axis;

import javafx.scene.canvas.Canvas;

public class InstantAxisRenderer extends AbstractAxisRenderer<Instant> {

    public InstantAxisRenderer(Canvas canvas, Axis<Instant> axis) {
	super(canvas, axis);
    }

}
