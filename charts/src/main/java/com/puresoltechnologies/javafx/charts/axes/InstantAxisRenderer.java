package com.puresoltechnologies.javafx.charts.axes;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puresoltechnologies.javafx.charts.plots.AbstractPlot;
import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.plots.PlotDatum;

import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class InstantAxisRenderer<A extends Axis<Instant>> extends AbstractAxisRenderer<Instant, A> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy MMM dd\nHH:mm");

    public InstantAxisRenderer(A axis, ObservableList<Plot<?, ?, ?>> plots) {
	super(axis, plots);
    }

    @Override
    public void scale(double factor, double ratioMinToMax) {
	Instant min = getMin();
	Instant max = getMax();
	if ((min != null) && (max != null)) {
	    long seconds = max.getEpochSecond() - min.getEpochSecond();
	    setMin(Instant.ofEpochSecond((long) (min.getEpochSecond() //
		    + (seconds * (factor * ratioMinToMax)))));
	    setMax(Instant.ofEpochSecond((long) (min.getEpochSecond() //
		    - (seconds * (factor * (1.0 - ratioMinToMax))))));
	}
    }

    @Override
    public void move(double fractionOfRange) {
	// TODO Auto-generated method stub
    }

    @Override
    protected void updateMinMax() {
	Axis<Instant> axis = getAxis();
	ObservableList<Plot<?, ?, ?>> plots = getPlots();
	Instant min = null;
	Instant max = null;
	switch (axis.getAxisType()) {
	case X:
	case ALT_X:
	    for (Plot<?, ?, ?> plot : plots) {
		if (plot.hasData()) {
		    min = calcMin(min, (Instant) plot.getMinX());
		    max = calcMax(max, (Instant) plot.getMaxX());
		}
	    }
	    break;
	case Y:
	case ALT_Y:
	    for (Plot<?, ?, ?> plot : plots) {
		if (plot.hasData()) {
		    min = calcMin(min, (Instant) plot.getMinY());
		    max = calcMax(max, (Instant) plot.getMaxY());
		}
	    }
	    break;
	default:
	    throw new IllegalStateException("Wrong type of axis found.");
	}
	setMin(min);
	setMax(max);
    }

    @Override
    protected double getLabelThickness() {
	Text text = new Text("WQ");
	text.setFont(getAxis().getLabelFont().toFont());
	text.applyCss();
	return 2 * text.getLayoutBounds().getHeight();
    }

    @Override
    public double calculatePos(double x, double y, double width, double height, Instant value) {
	updateMinMax(); // XXX find something more efficient
	long maxEpochSecond = getMax().getEpochSecond();
	long minEpochSecond = getMin().getEpochSecond();
	long currentEpochSecond = value.getEpochSecond();
	AxisType axisType = getAxis().getAxisType();
	switch (axisType) {
	case X:
	case ALT_X:
	    return x + ((width / (maxEpochSecond - minEpochSecond)) * (currentEpochSecond - minEpochSecond));
	case Y:
	case ALT_Y:
	    return (y + height)
		    - ((height / (maxEpochSecond - minEpochSecond)) * (currentEpochSecond - minEpochSecond));
	default:
	    throw new IllegalStateException("Unknown axis type '" + axisType + "' found.");
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void drawTicks(GraphicsContext gc, double x, double y, double width, double height) {
	AxisType axisType = getAxis().getAxisType();
	gc.setFont(getAxis().getLabelFont().toFont());
	List<Instant> possibleTicks = new ArrayList<>();
	switch (axisType) {
	case X:
	case ALT_X:
	    for (Plot<?, ?, ?> plot : getPlots()) {
		for (Object value : plot.getData()) {
		    Instant i = ((AbstractPlot<Instant, ?, PlotDatum<Instant, ?>>) plot)
			    .getAxisX((PlotDatum<Instant, ?>) value);
		    possibleTicks.add(i);
		}
	    }
	    break;
	case Y:
	case ALT_Y:
	    for (Plot<?, ?, ?> plot : getPlots()) {
		for (Object value : plot.getData()) {
		    Instant i = ((AbstractPlot<?, Instant, PlotDatum<?, Instant>>) plot)
			    .getAxisY((PlotDatum<?, Instant>) value);
		    possibleTicks.add(i);
		}
	    }
	    break;
	}
	Collections.sort(possibleTicks);
	double position = 0.0;
	for (Instant current : possibleTicks) {
	    long maxEpochSecond = getMax().getEpochSecond();
	    long minEpochSecond = getMin().getEpochSecond();
	    long currentEpochSecond = current.getEpochSecond();
	    double currentPosition;
	    switch (axisType) {
	    case X:
		currentPosition = (width / (maxEpochSecond - minEpochSecond)) * (currentEpochSecond - minEpochSecond);
		if (((currentPosition - position) < MIN_X_DISTANCE) && (position > 0.0)) {
		    continue;
		}
		position = currentPosition;
		gc.setStroke(getAxis().getColor());
		gc.setFill(getAxis().getColor());
		gc.strokeLine(x + position, y, x + position, y + AXIS_THICKNESS);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.TOP);
		gc.fillText(formatter.format(ZonedDateTime.ofInstant(current, ZoneId.of("UTC"))), x + position,
			y + AXIS_THICKNESS);
		break;
	    case ALT_X:
		currentPosition = (width / (maxEpochSecond - minEpochSecond)) * (currentEpochSecond - minEpochSecond);
		if ((currentPosition - position) < MIN_X_DISTANCE) {
		    continue;
		}
		position = currentPosition;
		gc.setStroke(getAxis().getColor());
		gc.setFill(getAxis().getColor());
		gc.strokeLine(x + position, y + height, x + position, (y + height) - AXIS_THICKNESS);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.BOTTOM);
		gc.fillText(formatter.format(ZonedDateTime.ofInstant(current, ZoneId.of("UTC"))), x + position,
			(y + height) - AXIS_THICKNESS);
		break;
	    case Y:
		currentPosition = (height / (maxEpochSecond - minEpochSecond)) * (currentEpochSecond - minEpochSecond);
		if ((currentPosition - position) < MIN_X_DISTANCE) {
		    continue;
		}
		position = currentPosition;
		gc.setStroke(getAxis().getColor());
		gc.setFill(getAxis().getColor());
		gc.strokeLine((x + width) - AXIS_THICKNESS, (y + height) - position, x + width,
			(y + height) - position);
		gc.setTextAlign(TextAlignment.RIGHT);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText(formatter.format(ZonedDateTime.ofInstant(current, ZoneId.of("UTC"))),
			(x + width) - AXIS_THICKNESS, (y + height) - position);
		break;
	    case ALT_Y:
		currentPosition = (height / (maxEpochSecond - minEpochSecond)) * (currentEpochSecond - minEpochSecond);
		if ((currentPosition - position) < MIN_X_DISTANCE) {
		    continue;
		}
		position = currentPosition;
		gc.setStroke(getAxis().getColor());
		gc.setFill(getAxis().getColor());
		gc.strokeLine(x, (y + height) - position, x + AXIS_THICKNESS, (y + height) - position);
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText(formatter.format(ZonedDateTime.ofInstant(current, ZoneId.of("UTC"))), x + AXIS_THICKNESS,
			(y + height) - position);
		break;
	    }
	}
    }

}
