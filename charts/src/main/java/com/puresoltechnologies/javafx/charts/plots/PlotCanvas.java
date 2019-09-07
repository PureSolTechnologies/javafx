package com.puresoltechnologies.javafx.charts.plots;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puresoltechnologies.javafx.charts.axes.Axis;
import com.puresoltechnologies.javafx.charts.axes.AxisRenderer;
import com.puresoltechnologies.javafx.charts.axes.AxisRendererFactory;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlotCanvas extends Canvas {

    protected final ObjectProperty<Color> frameColor = new SimpleObjectProperty<>(Color.BLACK);
    protected final ObjectProperty<Color> backgroundColor = new SimpleObjectProperty<>(Color.WHITE);
    protected final ObjectProperty<Insets> padding = new SimpleObjectProperty<>(new Insets(5.0));

    private final ObservableList<Plot<?, ?, ?>> plots = FXCollections.observableArrayList();
    private final List<Axis<?>> xAxes = new ArrayList<>();
    private final List<Axis<?>> yAxes = new ArrayList<>();
    private final List<Axis<?>> altXAxes = new ArrayList<>();
    private final List<Axis<?>> altYAxes = new ArrayList<>();
    private final Map<Axis<?>, AxisRenderer<?>> renderers = new HashMap<>();
    private final Map<Axis<?>, ObservableList<Plot<?, ?, ?>>> affectedPlots = new HashMap<>();

    public PlotCanvas() {
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

    public ObjectProperty<Color> frameColorProperty() {
	return frameColor;
    }

    public ObjectProperty<Color> backgroundColorProperty() {
	return backgroundColor;
    }

    public ObjectProperty<Insets> paddingProperty() {
	return padding;
    }

    public Insets getPadding() {
	return padding.getValue();
    }

    public void setPadding(Insets insets) {
	padding.setValue(insets);
    }

    public ObservableList<Plot<?, ?, ?>> getPlots() {
	return plots;
    }

    public void addPlot(Plot<?, ?, ?> plot) {
	plots.add(plot);
	Axis<?> xAxis = plot.getXAxis();
	switch (xAxis.getAxisType()) {
	case X:
	    if (!xAxes.contains(xAxis)) {
		xAxes.add(xAxis);
	    }
	    break;
	case ALT_X:
	    if (!altXAxes.contains(xAxis)) {
		altXAxes.add(xAxis);
	    }
	    break;
	default:
	    throw new RuntimeException("Invalid X axis type '" + xAxis.getAxisType() + "' found.");
	}
	Axis<?> yAxis = plot.getYAxis();
	switch (yAxis.getAxisType()) {
	case Y:
	    if (!yAxes.contains(yAxis)) {
		yAxes.add(yAxis);
	    }
	    break;
	case ALT_Y:
	    if (!altYAxes.contains(yAxis)) {
		altYAxes.add(yAxis);
	    }
	    break;
	default:
	    throw new RuntimeException("Invalid X axis type '" + yAxis.getAxisType() + "' found.");
	}

	for (Axis<?> axis : Arrays.asList(xAxis, yAxis)) {
	    if (!renderers.containsKey(axis)) {
		ObservableList<Plot<?, ?, ?>> plots = FXCollections.observableArrayList();
		plots.add(plot);
		affectedPlots.put(axis, plots);
	    } else {
		affectedPlots.get(axis).add(plot);
	    }
	    renderers.put(axis, AxisRendererFactory.forAxis(this, axis, affectedPlots.get(axis)));
	}
	plot.data().addListener((ListChangeListener<Object>) change -> draw());
    }

    private void draw() {
	clearPlotArea();
	Insets padding = this.padding.getValue();
	Rectangle frameArea = new Rectangle( //
		padding.getLeft(), //
		padding.getTop(), //
		getWidth() - padding.getLeft() - padding.getRight(), //
		getHeight() - padding.getTop() - padding.getBottom() //
	);
	Rectangle plotArea = drawAxes(frameArea);
	drawPlots(plotArea);
	drawFrame();
    }

    private void clearPlotArea() {
	GraphicsContext gc = getGraphicsContext2D();
	gc.setFill(backgroundColor.get());
	gc.setStroke(backgroundColor.get());
	gc.fillRect(0.0, 0.0, getWidth(), getHeight());
    }

    private void drawFrame() {
	GraphicsContext gc = getGraphicsContext2D();
	gc.setStroke(frameColor.get());
	gc.strokeRect( //
		0.0, //
		0.0, //
		getWidth(), //
		getHeight() //
	);
    }

    /**
     * Draw the axes and returns the remaining plotting area.
     *
     * @return
     */
    private Rectangle drawAxes(Rectangle plottingArea) {
	double xAxesThickness = calculateThickness(xAxes, renderers);
	double yAxesThickness = calculateThickness(yAxes, renderers);
	double altXAxesThickness = calculateThickness(altXAxes, renderers);
	double altYAxesThickness = calculateThickness(altYAxes, renderers);

	double position = (plottingArea.getY() + plottingArea.getHeight()) - xAxesThickness;
	for (Axis<?> axis : xAxes) {
	    AxisRenderer<?> renderer = renderers.get(axis);
	    double tickness = renderer.getTickness();
	    renderer.renderTo(this, plottingArea.getX() + yAxesThickness, position,
		    plottingArea.getWidth() - yAxesThickness - altYAxesThickness, tickness);
	    position += tickness;
	}
	position = plottingArea.getX();
	for (Axis<?> axis : yAxes) {
	    AxisRenderer<?> renderer = renderers.get(axis);
	    double tickness = renderer.getTickness();
	    renderer.renderTo(this, position, plottingArea.getY() + altXAxesThickness, tickness,
		    plottingArea.getHeight() - xAxesThickness - altXAxesThickness);
	    position += tickness;
	}
	position = plottingArea.getY();
	for (Axis<?> axis : altXAxes) {
	    AxisRenderer<?> renderer = renderers.get(axis);
	    double tickness = renderer.getTickness();
	    renderer.renderTo(this, plottingArea.getX() + yAxesThickness, position,
		    plottingArea.getWidth() - yAxesThickness - altYAxesThickness, tickness);
	    position += tickness;
	}
	position = (plottingArea.getX() + plottingArea.getWidth()) - altYAxesThickness;
	for (Axis<?> axis : altYAxes) {
	    AxisRenderer<?> renderer = renderers.get(axis);
	    double tickness = renderer.getTickness();
	    renderer.renderTo(this, position, plottingArea.getY() + altXAxesThickness, tickness,
		    plottingArea.getHeight() - xAxesThickness - altXAxesThickness);
	    position += tickness;
	}

	return new Rectangle( //
		plottingArea.getX() + yAxesThickness, //
		plottingArea.getY() + altXAxesThickness, //
		plottingArea.getWidth() - yAxesThickness - altYAxesThickness, //
		plottingArea.getHeight() - xAxesThickness - altXAxesThickness //
	);
    }

    private void drawPlots(Rectangle plottingArea) {
	for (Plot<?, ?, ?> plot : plots) {
	    if (plot.hasData()) {
		PlotRenderer<?, ?, ?, ?, ?> plotRenderer = ((AbstractPlot<?, ?, ?>) plot)
			.getGenericRenderer(renderers.get(plot.getXAxis()), renderers.get(plot.getYAxis()));
		plotRenderer.renderTo(this, plottingArea.getX(), plottingArea.getY(), plottingArea.getWidth(),
			plottingArea.getHeight());
	    }
	}
    }

    private double calculateThickness(List<Axis<?>> axes, Map<Axis<?>, AxisRenderer<?>> renderers) {
	double thickness = 0.0;
	for (Axis<?> axis : axes) {
	    thickness += renderers.get(axis).getTickness();
	}
	return thickness;
    }

}
