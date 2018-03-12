package com.puresoltechnologies.javafx.charts.plots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puresoltechnologies.javafx.charts.renderer.axis.AxisRenderer;
import com.puresoltechnologies.javafx.charts.renderer.axis.AxisRendererFactory;
import com.puresoltechnologies.streaming.CompositeStreamIterator;
import com.puresoltechnologies.streaming.StreamIterable;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlotArea extends Canvas {

    private final List<Plot<?, ?, ?>> plots = new ArrayList<>();

    public PlotArea() {
	super();
	widthProperty().addListener(event -> draw());
	heightProperty().addListener(event -> draw());
	draw();
    }

    @Override
    public double minHeight(double height) {
	return 200;
    }

    @Override
    public double maxHeight(double height) {
	return 10000;
    }

    @Override
    public double prefHeight(double height) {
	return minHeight(height);
    }

    @Override
    public double minWidth(double width) {
	return 320;
    }

    @Override
    public double maxWidth(double width) {
	return 10000;
    }

    @Override
    public double prefWidth(double width) {
	return minWidth(width);
    }

    @Override
    public boolean isResizable() {
	return true;
    }

    @Override
    public void resize(double width, double height) {
	super.resize(width, height);
	setWidth(width);
	setHeight(height);
	draw();
    }

    public void addPlot(Plot<?, ?, ?> plot) {
	plots.add(plot);
    }

    private void draw() {
	clearPlotArea();
	Rectangle plottingArea = drawAxes();
	drawFrame();
    }

    private void clearPlotArea() {
	double width = getWidth();
	double height = getHeight();
	GraphicsContext gc = getGraphicsContext2D();
	gc.setFill(Color.WHITE);
	gc.setStroke(Color.WHITE);
	gc.fillRect(0.0, 0.0, width, height);
    }

    private void drawFrame() {
	double width = getWidth();
	double height = getHeight();
	GraphicsContext gc = getGraphicsContext2D();
	gc.setStroke(Color.BLACK);
	gc.strokeRect(0.0, 0.0, width, height);
    }

    private Rectangle drawAxes() {
	List<Axis<?>> xAxes = new ArrayList<>();
	List<Axis<?>> yAxes = new ArrayList<>();
	List<Axis<?>> altXAxes = new ArrayList<>();
	List<Axis<?>> altYAxes = new ArrayList<>();
	plots.forEach(plot -> {
	    Axis<?> xAxis = plot.getXAxis();
	    switch (xAxis.getAxisType()) {
	    case X:
		xAxes.add(xAxis);
		break;
	    case ALT_X:
		altXAxes.add(xAxis);
		break;
	    default:
		throw new RuntimeException("Invalid X axis type '" + xAxis.getAxisType() + "' found.");
	    }
	    Axis<?> yAxis = plot.getYAxis();
	    switch (yAxis.getAxisType()) {
	    case Y:
		yAxes.add(yAxis);
		break;
	    case ALT_Y:
		altYAxes.add(yAxis);
		break;
	    default:
		throw new RuntimeException("Invalid X axis type '" + yAxis.getAxisType() + "' found.");
	    }
	});
	Map<Axis<?>, AxisRenderer> renderers = new HashMap<>();
	for (Axis<?> axis : StreamIterable.of(CompositeStreamIterator.of(xAxes.iterator(), yAxes.iterator(),
		altXAxes.iterator(), altYAxes.iterator()))) {
	    List<Plot<?, ?, ?>> affectedPlots = new ArrayList<>();
	    plots.forEach(plot -> {
		if (plot.getXAxis().equals(axis)) {
		    affectedPlots.add(plot);
		} else if (plot.getYAxis().equals(axis)) {
		    affectedPlots.add(plot);
		}
	    });
	    if (!renderers.containsKey(axis)) {
		renderers.put(axis, AxisRendererFactory.forAxis(this, axis, affectedPlots));
	    }
	}
	double xAxesThickness = calculateThickness(xAxes, renderers);
	double yAxesThickness = calculateThickness(yAxes, renderers);
	double altXAxesThickness = calculateThickness(altXAxes, renderers);
	double altYAxesThickness = calculateThickness(altYAxes, renderers);

	double position = getHeight() - xAxesThickness;
	for (Axis<?> axis : xAxes) {
	    AxisRenderer renderer = renderers.get(axis);
	    double tickness = renderer.getTickness();
	    renderer.renderTo(yAxesThickness, position, getWidth() - yAxesThickness - altYAxesThickness, tickness);
	    position += tickness;
	}
	position = 0.0;
	for (Axis<?> axis : yAxes) {
	    AxisRenderer renderer = renderers.get(axis);
	    double tickness = renderer.getTickness();
	    renderer.renderTo(position, altXAxesThickness, tickness, getHeight() - xAxesThickness - altXAxesThickness);
	    position += tickness;
	}
	position = 0.0;
	for (Axis<?> axis : altXAxes) {
	    AxisRenderer renderer = renderers.get(axis);
	    double tickness = renderer.getTickness();
	    renderer.renderTo(yAxesThickness, position, getWidth() - yAxesThickness - altYAxesThickness, tickness);
	    position += tickness;
	}
	position = getWidth() - altYAxesThickness;
	for (Axis<?> axis : altYAxes) {
	    AxisRenderer renderer = renderers.get(axis);
	    double tickness = renderer.getTickness();
	    renderer.renderTo(position, altXAxesThickness, tickness, getHeight() - xAxesThickness - altXAxesThickness);
	    position += tickness;
	}

	double width = getWidth();
	double height = getHeight();
	return new Rectangle( //
		yAxesThickness, //
		altXAxesThickness, //
		width - yAxesThickness - altYAxesThickness, //
		height - xAxesThickness - altXAxesThickness //
	);
    }

    private double calculateThickness(List<Axis<?>> axes, Map<Axis<?>, AxisRenderer> renderers) {
	double thickness = 0.0;
	for (Axis<?> axis : axes) {
	    thickness += renderers.get(axis).getTickness();
	}
	return thickness;
    }

}
