package com.puresoltechnologies.javafx.charts.renderer.axis;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puresoltechnologies.javafx.charts.plots.AbstractPlot;
import com.puresoltechnologies.javafx.charts.plots.Axis;
import com.puresoltechnologies.javafx.charts.plots.AxisType;
import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class InstantAxisRenderer extends AbstractAxisRenderer<Instant> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy MMM dd\nHH:mm");
    private final Instant min;
    private final Instant max;

    public InstantAxisRenderer(Canvas canvas, Axis<Instant> axis, List<Plot<?, ?, ?>> plots) {
	super(canvas, axis, plots);
	Instant min = null;
	Instant max = null;
	switch (axis.getAxisType()) {
	case X:
	case ALT_X:
	    for (Plot<?, ?, ?> plot : plots) {
		min = calcMin(min, (Instant) plot.getData().getMinX());
		max = calcMax(max, (Instant) plot.getData().getMaxX());
	    }
	    break;
	case Y:
	case ALT_Y:
	    for (Plot<?, ?, ?> plot : plots) {
		min = calcMin(min, (Instant) plot.getData().getMinY());
		max = calcMax(max, (Instant) plot.getData().getMaxY());
	    }
	    break;
	default:
	    throw new IllegalStateException("Wrong type of axis found.");
	}
	this.min = min;
	this.max = max;
    }

    @Override
    protected double getLabelThickness() {
	Text text = new Text("WQ");
	text.setFont(AXIS_LABEL_FONT);
	text.applyCss();
	return 2 * text.getLayoutBounds().getHeight();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void drawTicks(GraphicsContext gc, double x, double y, double width, double height) {
	AxisType axisType = getAxis().getAxisType();
	gc.setFill(Color.BLACK);
	gc.setStroke(Color.BLACK);
	gc.setFont(AXIS_LABEL_FONT);
	List<Instant> possibleTicks = new ArrayList<>();
	switch (axisType) {
	case X:
	case ALT_X:
	    for (Plot<?, ?, ?> plot : getPlots()) {
		for (Object value : plot.getData().getData()) {
		    Instant i = ((AbstractPlot<Instant, ?, Object>) plot).getAxisX(value);
		    possibleTicks.add(i);
		}
	    }
	    break;
	case Y:
	case ALT_Y:
	    for (Plot<?, ?, ?> plot : getPlots()) {
		for (Object value : plot.getData().getData()) {
		    Instant i = ((AbstractPlot<?, Instant, Object>) plot).getAxisY(value);
		    possibleTicks.add(i);
		}
	    }
	    break;
	}
	Collections.sort(possibleTicks);
	double position = 0.0;
	for (Instant current : possibleTicks) {
	    long maxEpochSecond = max.getEpochSecond();
	    long minEpochSecond = min.getEpochSecond();
	    long currentEpochSecond = current.getEpochSecond();
	    double currentPosition;
	    switch (axisType) {
	    case X:
		currentPosition = width / (maxEpochSecond - minEpochSecond) * (currentEpochSecond - minEpochSecond);
		if ((currentPosition - position < MIN_DISTANCE) && (position > 0.0)) {
		    continue;
		}
		position = currentPosition;
		gc.strokeLine(x + position, y, x + position, y + AXIS_THICKNESS);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.TOP);
		gc.fillText(formatter.format(ZonedDateTime.ofInstant(current, ZoneId.of("UTC"))), x + position,
			y + AXIS_THICKNESS);
		break;
	    case ALT_X:
		currentPosition = width / (maxEpochSecond - minEpochSecond) * (currentEpochSecond - minEpochSecond);
		if (currentPosition - position < MIN_DISTANCE) {
		    continue;
		}
		position = currentPosition;
		gc.strokeLine(x + position, y + height, x + position, y + height - AXIS_THICKNESS);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.BOTTOM);
		gc.fillText(formatter.format(ZonedDateTime.ofInstant(current, ZoneId.of("UTC"))), x + position,
			y + height - AXIS_THICKNESS);
		break;
	    case Y:
		currentPosition = height / (maxEpochSecond - minEpochSecond) * (currentEpochSecond - minEpochSecond);
		if (currentPosition - position < MIN_DISTANCE) {
		    continue;
		}
		position = currentPosition;
		gc.strokeLine(x + width - AXIS_THICKNESS, y + height - position, x + width, y + height - position);
		gc.setTextAlign(TextAlignment.RIGHT);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText(formatter.format(ZonedDateTime.ofInstant(current, ZoneId.of("UTC"))),
			x + width - AXIS_THICKNESS, y + height - position);
		break;
	    case ALT_Y:
		currentPosition = height / (maxEpochSecond - minEpochSecond) * (currentEpochSecond - minEpochSecond);
		if (currentPosition - position < MIN_DISTANCE) {
		    continue;
		}
		position = currentPosition;
		gc.strokeLine(x, y + height - position, x + AXIS_THICKNESS, y + height - position);
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText(formatter.format(ZonedDateTime.ofInstant(current, ZoneId.of("UTC"))), x + AXIS_THICKNESS,
			y + height - position);
		break;
	    }
	}
    }

}
