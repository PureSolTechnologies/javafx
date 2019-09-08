package com.puresoltechnologies.javafx.charts.axes;

import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.utils.TickCalculator;

import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class NumberAxisRenderer<T extends Number & Comparable<T>, A extends NumberAxis<T>>
	extends AbstractAxisRenderer<T, A> {

    /**
     * Keeps the accuracy of the tick numbers as exponent to base 10.
     */
    private int accuracy = 0;
    private double tickSteps = 1;

    public NumberAxisRenderer(A axis, ObservableList<Plot<?, ?, ?>> plots) {
	super(axis, plots);
    }

    @Override
    protected void updateMinMax() {
	A axis = getAxis();
	ObservableList<Plot<?, ?, ?>> plots = getPlots();
	T min = null;
	T max = null;
	switch (axis.getAxisType()) {
	case X:
	case ALT_X:
	    for (Plot<?, ?, ?> plot : plots) {
		if (plot.hasData()) {
		    min = calcMin(min, (T) plot.getMinX());
		    max = calcMax(max, (T) plot.getMaxX());
		}
	    }
	    break;
	case Y:
	case ALT_Y:
	    for (Plot<?, ?, ?> plot : plots) {
		if (plot.hasData()) {
		    min = calcMin(min, (T) plot.getMinY());
		    max = calcMax(max, (T) plot.getMaxY());
		}
	    }
	    break;
	default:
	    throw new IllegalStateException("Wrong type of axis found.");
	}
	int accuracyExponent = 0;
	if ((min != null) && (max != null)) {
	    accuracyExponent = TickCalculator.calculateAccuracy(min.doubleValue(), max.doubleValue());
	    if (Double.class.isAssignableFrom(min.getClass())) {
		min = (T) Double.valueOf(TickCalculator.calculateChartMin(min.doubleValue(), accuracyExponent));
		max = (T) Double.valueOf(TickCalculator.calculateChartMax(max.doubleValue(), accuracyExponent));
	    } else if (Float.class.isAssignableFrom(min.getClass())) {
		min = (T) Float.valueOf((float) TickCalculator.calculateChartMin(min.floatValue(), accuracyExponent));
		max = (T) Float.valueOf((float) TickCalculator.calculateChartMax(max.floatValue(), accuracyExponent));
	    }
	    // optimize min and max
	}
	setAccuracy(accuracyExponent);
	setMin(min);
	setMax(max);
    }

    private void setAccuracy(int accuracy) {
	this.accuracy = accuracy;
	this.tickSteps = Math.pow(10.0, accuracy);
    }

    public int getAccuracy() {
	return accuracy;
    }

    @Override
    public double calculatePos(double x, double y, double width, double height, Number value) {
	double d = value.doubleValue();
	if ((getMin() != null) && (getMax() != null)) {
	    return super.calculatePos(x, y, width, height, getMin().doubleValue(), getMax().doubleValue(), d);
	} else {
	    return super.calculatePos(x, y, width, height, d, d, d);
	}
    }

    @Override
    protected double getLabelThickness() {
	Text text = new Text("W1.234");
	text.setFont(getAxis().getLabelFont().toFont());
	text.applyCss();
	switch (getAxis().getAxisType()) {
	case X:
	case ALT_X:
	    return text.getLayoutBounds().getHeight();
	case Y:
	case ALT_Y:
	    return text.getLayoutBounds().getWidth();
	default:
	    return text.getLayoutBounds().getHeight();
	}
    }

    @Override
    protected void drawTicks(GraphicsContext gc, double x, double y, double width, double height) {
	AxisType axisType = getAxis().getAxisType();
	double position;
	double minDinstance;
	switch (axisType) {
	case X:
	case ALT_X:
	    position = x;
	    minDinstance = MIN_X_DISTANCE;
	    break;
	case Y:
	case ALT_Y:
	    position = y + height;
	    minDinstance = MIN_Y_DISTANCE;
	    break;
	default:
	    throw new IllegalStateException("Unknown axis type '" + axisType + "' found.");
	}
	String formatString = "%.0f";
	if (accuracy < 0) {
	    formatString = "%." + Math.abs(accuracy) + "f";
	}
	boolean first = true;
	for (double current = getMin().doubleValue(); current <= getMax().doubleValue(); current += tickSteps) {
	    double currentPosition = calculatePos(x, y, width, height, current);
	    if ((Math.abs(currentPosition - position) < minDinstance) && !first) {
		continue;
	    }
	    position = currentPosition;
	    gc.setFill(getAxis().getColor());
	    gc.setStroke(getAxis().getColor());
	    switch (axisType) {
	    case X:
		gc.strokeLine(position, y, position, y + ((AXIS_THICKNESS * 2) / 3));
		break;
	    case ALT_X:
		gc.strokeLine(position, y + height, position, (y + height) - ((AXIS_THICKNESS * 2) / 3));
		break;
	    case Y:
		gc.strokeLine((x + width) - ((AXIS_THICKNESS * 2) / 3), position, x + width, position);
		break;
	    case ALT_Y:
		gc.strokeLine(x, position, x + ((AXIS_THICKNESS * 2) / 3), position);
		break;
	    }
	    gc.setFont(getAxis().getLabelFont().toFont());
	    String tickLabel = String.format(formatString, current);
	    switch (axisType) {
	    case X:
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.TOP);
		gc.fillText(tickLabel, position, y + AXIS_THICKNESS);
		break;
	    case ALT_X:
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.BOTTOM);
		gc.fillText(tickLabel, position, (y + height) - AXIS_THICKNESS);
		break;
	    case Y:
		gc.setTextAlign(TextAlignment.RIGHT);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText(tickLabel, (x + width) - AXIS_THICKNESS, position);
		break;
	    case ALT_Y:
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText(tickLabel, x + AXIS_THICKNESS, position);
		break;
	    }
	    first = false;
	}
    }

}
