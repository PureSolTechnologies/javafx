package com.puresoltechnologies.javafx.charts.renderer.axes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puresoltechnologies.javafx.charts.axes.Axis;
import com.puresoltechnologies.javafx.charts.axes.AxisType;
import com.puresoltechnologies.javafx.charts.plots.AbstractPlot;
import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class NumberAxisRenderer extends AbstractAxisRenderer<Number> {

    private final double min;
    private final double max;

    public NumberAxisRenderer(Canvas canvas, Axis<Number> axis, List<Plot<?, ?, ?>> plots) {
	super(canvas, axis, plots);
	Double min = null;
	Double max = null;
	switch (axis.getAxisType()) {
	case X:
	case ALT_X:
	    for (Plot<?, ?, ?> plot : plots) {
		min = calcMin(min, ((Number) plot.getData().getMinX()).doubleValue());
		max = calcMax(max, ((Number) plot.getData().getMaxX()).doubleValue());
	    }
	    break;
	case Y:
	case ALT_Y:
	    for (Plot<?, ?, ?> plot : plots) {
		min = calcMin(min, ((Number) plot.getData().getMinY()).doubleValue());
		max = calcMax(max, ((Number) plot.getData().getMaxY()).doubleValue());
	    }
	    break;
	default:
	    throw new IllegalStateException("Wrong type of axis found.");
	}
	this.min = min;
	this.max = max;
    }

    @Override
    public double calculatePos(double x, double y, double width, double height, Number value) {
	return super.calculatePos(x, y, width, height, min, max, value.doubleValue());
    }

    @Override
    public Double getMin() {
	return min;
    }

    @Override
    public Double getMax() {
	return max;
    }

    @Override
    protected double getLabelThickness() {
	Text text = new Text("WQ");
	text.setFont(AXIS_LABEL_FONT);
	text.applyCss();
	return text.getLayoutBounds().getHeight();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void drawTicks(GraphicsContext gc, double x, double y, double width, double height) {
	AxisType axisType = getAxis().getAxisType();
	gc.setFill(Color.BLACK);
	gc.setStroke(Color.BLACK);
	gc.setFont(AXIS_LABEL_FONT);
	List<Double> possibleTicks = new ArrayList<>();
	switch (axisType) {
	case X:
	case ALT_X:
	    for (Plot<?, ?, ?> plot : getPlots()) {
		for (Object value : plot.getData().getData()) {
		    Double i = ((AbstractPlot<Double, ?, Object>) plot).getAxisX(value);
		    possibleTicks.add(i);
		}
	    }
	    break;
	case Y:
	case ALT_Y:
	    for (Plot<?, ?, ?> plot : getPlots()) {
		for (Object value : plot.getData().getData()) {
		    Double i = ((AbstractPlot<?, Double, Object>) plot).getAxisY(value);
		    possibleTicks.add(i);
		}
	    }
	    break;
	}
	Collections.sort(possibleTicks);
	double position;
	switch (axisType) {
	case X:
	case ALT_X:
	    position = x;
	    break;
	case Y:
	case ALT_Y:
	    position = y + height;
	    break;
	default:
	    throw new IllegalStateException("Unknown axis type '" + axisType + "' found.");
	}
	for (double current : possibleTicks) {
	    double currentPosition;
	    switch (axisType) {
	    case X:
		currentPosition = calculatePos(x, y, width, height, current);
		if ((currentPosition - position < MIN_DISTANCE) && (position > x)) {
		    continue;
		}
		position = currentPosition;
		gc.strokeLine(x + position, y, x + position, y + AXIS_THICKNESS);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.TOP);
		gc.fillText(String.valueOf(current), x + position, y + AXIS_THICKNESS);
		break;
	    case ALT_X:
		currentPosition = calculatePos(x, y, width, height, current);
		if ((currentPosition - position < MIN_DISTANCE) && (position > x)) {
		    continue;
		}
		position = currentPosition;
		gc.strokeLine(x + position, y + height, x + position, y + height - AXIS_THICKNESS);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.BOTTOM);
		gc.fillText(String.valueOf(current), x + position, y + height - AXIS_THICKNESS);
		break;
	    case Y:
		currentPosition = calculatePos(x, y, width, height, current);
		if ((position - currentPosition < MIN_DISTANCE) && (currentPosition < y + height)) {
		    continue;
		}
		position = currentPosition;
		gc.strokeLine(x + width - AXIS_THICKNESS, y + height - position, x + width, y + height - position);
		gc.setTextAlign(TextAlignment.RIGHT);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText(String.valueOf(current), x + width - AXIS_THICKNESS, y + height - position);
		break;
	    case ALT_Y:
		currentPosition = calculatePos(x, y, width, height, current);
		if ((position - currentPosition < MIN_DISTANCE) && (currentPosition < y + height)) {
		    continue;
		}
		position = currentPosition;
		gc.strokeLine(x, y + height - position, x + AXIS_THICKNESS, y + height - position);
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText(String.valueOf(current), x + AXIS_THICKNESS, y + height - position);
		break;
	    }
	}
    }

}
