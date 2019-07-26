package com.puresoltechnologies.javafx.charts.axes;

import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.utils.TickCalculator;

import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class NumberAxisRenderer extends AbstractAxisRenderer<Number> {

    /**
     * Keeps the accuracy of the tick numbers as exponent to base 10.
     */
    private int accuracy = 0;
    private double tickSteps = 1;

    public NumberAxisRenderer(Axis<Number> axis, ObservableList<Plot<?, ?, ?>> plots) {
	super(axis, plots);
    }

    @Override
    protected void updateMinMax() {
	Axis<Number> axis = getAxis();
	ObservableList<Plot<?, ?, ?>> plots = getPlots();
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
	int accuracyExponent = 0;
	if ((min != null) && (max != null)) {
	    // optimize min and max
	    accuracyExponent = TickCalculator.calculateAccuracy(min, max);
	    min = TickCalculator.calculateChartMin(min, accuracyExponent);
	    max = TickCalculator.calculateChartMin(max, accuracyExponent);
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
	text.setFont(axisLabelFont.get().toFont());
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

    @SuppressWarnings("unchecked")
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
	    if ((Math.abs(currentPosition - position) < minDinstance) && (!first)) {
		continue;
	    }
	    position = currentPosition;
	    gc.setFill(axisColor.get());
	    gc.setStroke(axisColor.get());
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
	    gc.setFont(axisLabelFont.get().toFont());
	    gc.setStroke(axisTitleFont.get().getColor());
	    gc.setFill(axisTitleFont.get().getColor());
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
