package com.puresoltechnologies.javafx.charts.axes;

import com.puresoltechnologies.javafx.charts.AbstractRenderer;
import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.preferences.ChartsProperties;
import com.puresoltechnologies.javafx.extensions.fonts.FontDefinition;
import com.puresoltechnologies.javafx.preferences.Preferences;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public abstract class AbstractAxisRenderer<T> extends AbstractRenderer implements AxisRenderer<T> {

    protected static final double AXIS_THICKNESS = 10.0;
    protected static final double MIN_X_DISTANCE = 50.0;
    protected static final double MIN_Y_DISTANCE = 25.0;

    protected static final ObjectProperty<Color> backgroundColor = Preferences
	    .getProperty(ChartsProperties.BACKGROUND_COLOR);
    protected static final ObjectProperty<Color> axisColor = Preferences.getProperty(ChartsProperties.AXIS_COLOR);
    protected static final ObjectProperty<FontDefinition> axisLabelFont = Preferences
	    .getProperty(ChartsProperties.AXIS_LABEL_FONT);
    protected static final ObjectProperty<FontDefinition> axisTitleFont = Preferences
	    .getProperty(ChartsProperties.AXIS_TITLE_FONT);

    private final Axis<T> axis;
    private final ObservableList<Plot<?, ?, ?>> plots;

    private T min = null;
    private T max = null;

    public AbstractAxisRenderer(Axis<T> axis, ObservableList<Plot<?, ?, ?>> plots) {
	this.axis = axis;
	this.plots = plots;
	plots.addListener((ListChangeListener<Plot<?, ?, ?>>) c -> updateMinMax());
	plots.forEach(plot -> plot.data().addListener((ListChangeListener<Object>) arg0 -> updateMinMax()));
	updateMinMax();
    }

    protected abstract void updateMinMax();

    protected final void setMin(T min) {
	this.min = min;
    }

    @Override
    public final T getMin() {
	return min;
    }

    protected final void setMax(T max) {
	this.max = max;
    }

    @Override
    public final T getMax() {
	return max;
    }

    public final Axis<T> getAxis() {
	return axis;
    }

    protected final ObservableList<Plot<?, ?, ?>> getPlots() {
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
	text.setFont(axisTitleFont.get().toFont());
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
    public void renderTo(Canvas canvas, double x, double y, double width, double height) {
	GraphicsContext gc = canvas.getGraphicsContext2D();
	clearAxisArea(gc, x, y, width, height);
	drawAxis(gc, x, y, width, height);
	if (hasData()) {
	    drawTicks(gc, x, y, width, height);
	}
	renderAxisTitle(canvas, x, y, width, height);
    }

    private boolean hasData() {
	return getMin() != null && getMax() != null;
    }

    private void clearAxisArea(GraphicsContext gc, double x, double y, double width, double height) {
	gc.setFill(backgroundColor.get());
	gc.setStroke(backgroundColor.get());
	gc.fillRect(x, y, width, height);
    }

    private void drawAxis(GraphicsContext gc, double x, double y, double width, double height) {
	gc.setFill(axisColor.get());
	gc.setStroke(axisColor.get());
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

    private void renderAxisTitle(Canvas canvas, double x, double y, double width, double height) {
	GraphicsContext gc = canvas.getGraphicsContext2D();
	// TITLE
	String axisTitle = axis.getTitle();
	if (axis.getUnit() != null) {
	    axisTitle += " (" + axis.getUnit() + ")";
	}
	Text titleText = new Text(axisTitle);
	titleText.setFont(axisTitleFont.get().toFont());
	titleText.applyCss();
	// Set attributes
	gc.setStroke(axisTitleFont.get().getColor());
	gc.setFill(axisTitleFont.get().getColor());
	gc.setFont(axisTitleFont.get().toFont());
	gc.setTextAlign(TextAlignment.CENTER);
	gc.setTextBaseline(VPos.TOP);

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
