package com.puresoltechnologies.javafx.charts.axes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puresoltechnologies.javafx.charts.plots.AbstractPlot;
import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class NumberAxisRenderer extends AbstractAxisRenderer<Number> {

    private Double min = null;
    private Double max = null;

    public NumberAxisRenderer(Canvas canvas, Axis<Number> axis, List<Plot<?, ?, ?>> plots) {
	super(canvas, axis, plots);
    }

    @Override
    protected void updateMinMax() {
	Axis<Number> axis = getAxis();
	List<Plot<?, ?, ?>> plots = getPlots();
	Double min = null;
	Double max = null;
	switch (axis.getAxisType()) {
	case X:
	case ALT_X:
	    for (Plot<?, ?, ?> plot : plots) {
		if (plot.hasData()) {
		    min = calcMin(min, ((Number) plot.getMinX()).doubleValue());
		    max = calcMax(max, ((Number) plot.getMaxX()).doubleValue());
		}
	    }
	    break;
	case Y:
	case ALT_Y:
	    for (Plot<?, ?, ?> plot : plots) {
		if (plot.hasData()) {
		    min = calcMin(min, ((Number) plot.getMinY()).doubleValue());
		    max = calcMax(max, ((Number) plot.getMaxY()).doubleValue());
		}
	    }
	    break;
	default:
	    throw new IllegalStateException("Wrong type of axis found.");
	}
	this.min = min;
	this.max = max;
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
    public double calculatePos(double x, double y, double width, double height, Number value) {
	return super.calculatePos(x, y, width, height, min, max, value.doubleValue());
    }

    @Override
    protected double getLabelThickness() {
	Text text = new Text("WQ");
	text.setFont(axisLabelFont.get().toFont());
	text.applyCss();
	return text.getLayoutBounds().getHeight();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void drawTicks(GraphicsContext gc, double x, double y, double width, double height) {
	AxisType axisType = getAxis().getAxisType();
	gc.setFont(axisLabelFont.get().toFont());
	List<Double> possibleTicks = new ArrayList<>();
	switch (axisType) {
	case X:
	case ALT_X:
	    for (Plot<?, ?, ?> plot : getPlots()) {
		for (Object value : plot.getData()) {
		    Double i = ((AbstractPlot<Double, ?, Object>) plot).getAxisX(value);
		    possibleTicks.add(i);
		}
	    }
	    break;
	case Y:
	case ALT_Y:
	    for (Plot<?, ?, ?> plot : getPlots()) {
		for (Object value : plot.getData()) {
		    Number i = (Number) ((AbstractPlot<?, ?, Object>) plot).getAxisY(value);
		    possibleTicks.add(i.doubleValue());
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
		gc.setFill(axisColor.get());
		gc.setStroke(axisColor.get());
		gc.strokeLine(x + position, y, x + position, y + AXIS_THICKNESS);
		gc.setStroke(axisTitleFont.get().getColor());
		gc.setFill(axisTitleFont.get().getColor());
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
		gc.setFill(axisColor.get());
		gc.setStroke(axisColor.get());
		gc.strokeLine(x + position, y + height, x + position, y + height - AXIS_THICKNESS);
		gc.setStroke(axisTitleFont.get().getColor());
		gc.setFill(axisTitleFont.get().getColor());
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
		gc.setFill(axisColor.get());
		gc.setStroke(axisColor.get());
		gc.strokeLine(x + width - AXIS_THICKNESS, y + height - position, x + width, y + height - position);
		gc.setStroke(axisTitleFont.get().getColor());
		gc.setFill(axisTitleFont.get().getColor());
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
		gc.setFill(axisColor.get());
		gc.setStroke(axisColor.get());
		gc.strokeLine(x, y + height - position, x + AXIS_THICKNESS, y + height - position);
		gc.setStroke(axisTitleFont.get().getColor());
		gc.setFill(axisTitleFont.get().getColor());
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText(String.valueOf(current), x + AXIS_THICKNESS, y + height - position);
		break;
	    }
	}
    }

}