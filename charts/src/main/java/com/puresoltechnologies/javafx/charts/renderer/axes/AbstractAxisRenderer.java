package com.puresoltechnologies.javafx.charts.renderer.axes;

import java.util.List;

import com.puresoltechnologies.javafx.charts.axes.Axis;
import com.puresoltechnologies.javafx.charts.axes.AxisType;
import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.renderer.AbstractRenderer;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public abstract class AbstractAxisRenderer<T> extends AbstractRenderer implements AxisRenderer<T> {

    protected static final double AXIS_THICKNESS = 10.0;
    protected static final double MIN_DISTANCE = 100.0;
    protected static final Font AXIS_LABEL_FONT = Font.font("Arial", FontWeight.THIN, 10.0);
    protected static final Font AXIS_TITLE_FONT = Font.font("Arial", FontWeight.NORMAL, 12.0);

    private final Axis<T> axis;
    private final List<Plot<?, ?, ?>> plots;

    public AbstractAxisRenderer(Canvas canvas, Axis<T> axis, List<Plot<?, ?, ?>> plots) {
	super(canvas);
	this.axis = axis;
	this.plots = plots;
    }

    public final Axis<T> getAxis() {
	return axis;
    }

    protected final List<Plot<?, ?, ?>> getPlots() {
	return plots;
    }

    protected <M extends Comparable<M>> M calcMin(M currentMin, M newValue) {
	if (currentMin == null) {
	    return newValue;
	}
	if (currentMin.compareTo(newValue) < 0) {
	    return currentMin;
	}
	return newValue;
    }

    protected <M extends Comparable<M>> M calcMax(M currentMax, M newValue) {
	if (currentMax == null) {
	    return newValue;
	}
	if (currentMax.compareTo(newValue) > 0) {
	    return currentMax;
	}
	return newValue;
    }

    @Override
    public double getTickness() {
	double thickness = AXIS_THICKNESS;
	thickness += getLabelThickness();
	Text text = new Text(axis.getTitle());
	text.setFont(AXIS_TITLE_FONT);
	text.applyCss();
	thickness += text.getLayoutBounds().getHeight();
	return thickness;
    }

    public double calculatePos(double x, double y, double width, double height, double min, double max, double value) {
	AxisType axisType = getAxis().getAxisType();
	switch (axisType) {
	case X:
	case ALT_X:
	    return calcPosX(x, width, min, max, value);
	case Y:
	case ALT_Y:
	    return calcPosY(y, height, min, max, value);
	default:
	    throw new IllegalStateException("Unknown axis type '" + axisType + "' found.");
	}
    }

    public abstract double calculatePos(double x, double y, double width, double height, T value);

    protected abstract double getLabelThickness();

    @Override
    public void renderTo(double x, double y, double width, double height) {
	GraphicsContext gc = getCanvas().getGraphicsContext2D();
	clearAxisArea(gc, x, y, width, height);
	drawAxis(gc, x, y, width, height);
	drawTicks(gc, x, y, width, height);
	renderAxisTitle(gc, x, y, width, height);
	drawAxis(gc, x, y, width, height);
	drawTicks(gc, x, y, width, height);
	renderAxisTitle(gc, x, y, width, height);
    }

    private void clearAxisArea(GraphicsContext gc, double x, double y, double width, double height) {
	gc.setFill(Color.WHITE);
	gc.setStroke(Color.WHITE);
	gc.fillRect(x, y, width, height);
    }

    private void drawAxis(GraphicsContext gc, double x, double y, double width, double height) {
	gc.setFill(Color.BLACK);
	gc.setStroke(Color.BLACK);
	switch (axis.getAxisType()) {
	case X:
	    gc.strokeLine(x, y, x + width, y);
	    break;
	case ALT_X:
	    gc.strokeLine(x, y + height, x + width, y + height);
	    break;
	case Y:
	    gc.strokeLine(x + width, y, x + width, y + height);
	    break;
	case ALT_Y:
	    gc.strokeLine(x, y, x, y + height);
	    break;
	}
    }

    protected abstract void drawTicks(GraphicsContext gc, double x, double y, double width, double height);

    private void renderAxisTitle(GraphicsContext gc, double x, double y, double width, double height) {
	// TITLE
	String axisTitle = axis.getTitle();
	if (axis.getUnit() != null) {
	    axisTitle += " (" + axis.getUnit() + ")";
	}
	Text titleText = new Text(axisTitle);
	titleText.setFont(AXIS_TITLE_FONT);
	titleText.applyCss();
	// Set attributes
	gc.setFill(Color.BLACK);
	gc.setStroke(Color.BLACK);
	gc.setFont(AXIS_TITLE_FONT);
	gc.setTextAlign(TextAlignment.CENTER);
	gc.setTextBaseline(VPos.TOP);

	Canvas canvas = getCanvas();
	switch (axis.getAxisType()) {
	case X:
	    // Title
	    gc.fillText(axisTitle, x + width / 2.0, y + height - titleText.getLayoutBounds().getHeight());
	    break;
	case ALT_X:
	    // Title
	    gc.fillText(axisTitle, x + width / 2.0, y);
	    break;
	case Y:
	    // Title
	    gc.rotate(-90);
	    gc.fillText(axisTitle, -canvas.getHeight() / 2.0, 0.0);
	    gc.rotate(90);
	    break;
	case ALT_Y:
	    // Title
	    gc.rotate(-90);
	    gc.fillText(axisTitle, -canvas.getHeight() / 2.0,
		    canvas.getWidth() - titleText.getLayoutBounds().hashCode());
	    gc.rotate(90);
	    break;
	}
    }

}
