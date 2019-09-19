package com.puresoltechnologies.javafx.charts.plots;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.puresoltechnologies.javafx.charts.axes.Axis;
import com.puresoltechnologies.javafx.charts.axes.AxisRenderer;
import com.puresoltechnologies.javafx.charts.axes.AxisRendererFactory;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

public class PlotCanvas extends Canvas {

    protected final ObjectProperty<Color> frameColor = new SimpleObjectProperty<>(Color.BLACK);
    protected final ObjectProperty<Color> backgroundColor = new SimpleObjectProperty<>(Color.WHITE);
    protected final ObjectProperty<Insets> padding = new SimpleObjectProperty<>(new Insets(5.0));

    private final ObservableList<Plot<?, ?, ?>> plots = FXCollections.observableArrayList();
    private final Map<Plot<?, ?, ?>, PlotRenderer<?, ?, ?, ?, ?>> plotRenderers = new HashMap<>();
    private final List<Axis<?>> xAxes = new ArrayList<>();
    private final List<Axis<?>> yAxes = new ArrayList<>();
    private final List<Axis<?>> altXAxes = new ArrayList<>();
    private final List<Axis<?>> altYAxes = new ArrayList<>();
    private final Map<Axis<?>, AxisRenderer<?>> axisRenderers = new HashMap<>();
    private final Map<Axis<?>, ObservableList<Plot<?, ?, ?>>> affectedPlots = new HashMap<>();

    private Rectangle2D plotArea = null;

    public PlotCanvas() {
	super();
	widthProperty().addListener(event -> draw());
	heightProperty().addListener(event -> draw());

	Tooltip tooltip = new Tooltip();
	Tooltip.install(this, tooltip);
	setOnMouseMoved(e -> {
	    for (PlotRenderer<?, ?, ?, ?, ?> renderer : plotRenderers.values()) {
		Object v = renderer.findDataPoint(e.getX(), e.getY());
		if (v != null) {
		    tooltip.setText(v.toString());
		    return;
		}
	    }
	    tooltip.hide();
	});
	setOnMouseExited(e -> {
	    tooltip.hide();
	});
	setOnScroll(event -> {
	    if ( //
	    (event.isControlDown()) //
		    && (plotArea != null) //
		    && (plotArea.contains(event.getX(), event.getY())) //
	    ) {
		scale(event);
		event.consume();
	    }
	});

	setOnZoom(event -> {
	    if ((plotArea != null) && (plotArea.contains(event.getX(), event.getY()))) {
		System.err.println("zoom: factor=" + event.getTotalZoomFactor());
		event.consume();
	    }
	});

    }

    private void scale(ScrollEvent event) {
	for (Entry<Axis<?>, AxisRenderer<?>> entry : axisRenderers.entrySet()) {
	    Axis<?> axis = entry.getKey();
	    AxisRenderer<?> renderer = entry.getValue();
	    Rectangle2D location = renderer.getLocation();
	    double ratioMinToMax = 0.0;
	    switch (axis.getAxisType()) {
	    case X:
	    case ALT_X:
		double size = location.getWidth();
		ratioMinToMax = (event.getX() - location.getMinX()) / size;
		break;
	    case Y:
	    case ALT_Y:
		size = location.getHeight();
		ratioMinToMax = 1.0 - ((event.getY() - location.getMinY()) / size);
		break;
	    }
	    renderer.scale(Math.pow(1.1, event.getDeltaY() / event.getMultiplierY()), ratioMinToMax);
	    draw();
	}
    }

    @Override
    public double minHeight(double height) {
	return 200;
    }

    @Override
    public double maxHeight(double height) {
	return Double.MAX_VALUE;
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
	return Double.MAX_VALUE;
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

    public void autoScale() {
	for (AxisRenderer<?> renderer : axisRenderers.values()) {
	    renderer.setAutoScaleMin(true);
	    renderer.setAutoScaleMax(true);
	    draw();
	}
    }

    public ObjectProperty<Color> frameColorProperty() {
	return frameColor;
    }

    /**
     * This property specifies the background color of the plot. If
     * <code>null</code> is provided, the plot is transparent.
     *
     * @return An {@link ObjectProperty} of {@link Color} is returned.
     */
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
	    if (!axisRenderers.containsKey(axis)) {
		ObservableList<Plot<?, ?, ?>> plots = FXCollections.observableArrayList();
		plots.add(plot);
		affectedPlots.put(axis, plots);
	    } else {
		affectedPlots.get(axis).add(plot);
	    }
	    axisRenderers.put(axis, AxisRendererFactory.forAxis(this, axis, affectedPlots.get(axis)));
	}
	plot.data().addListener((ListChangeListener<Object>) change -> draw());
    }

    private void draw() {
	clear();
	Insets padding = this.padding.getValue();
	double x = padding.getLeft();
	double y = padding.getTop();
	double width = getWidth() - x - padding.getRight();
	double height = getHeight() - y - padding.getBottom();
	if ((width > 0) && (height > 0)) {
	    Rectangle2D frameArea = new Rectangle2D( //
		    x, y, width, height);
	    this.plotArea = drawAxes(frameArea);
	    drawPlots(plotArea);
	    drawFrame();
	}
    }

    private void clear() {
	GraphicsContext gc = getGraphicsContext2D();
	Color bgColor = backgroundColor.get();
	if (bgColor != null) {
	    gc.setFill(bgColor);
	    gc.setStroke(bgColor);
	    gc.fillRect(0.0, 0.0, getWidth(), getHeight());
	} else {
	    gc.clearRect(0.0, 0.0, getWidth(), getHeight());
	}
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
    private Rectangle2D drawAxes(Rectangle2D plottingArea) {
	double xAxesThickness = calculateThickness(xAxes, axisRenderers);
	double yAxesThickness = calculateThickness(yAxes, axisRenderers);
	double altXAxesThickness = calculateThickness(altXAxes, axisRenderers);
	double altYAxesThickness = calculateThickness(altYAxes, axisRenderers);

	double position = (plottingArea.getMinY() + plottingArea.getHeight()) - xAxesThickness;
	for (Axis<?> axis : xAxes) {
	    AxisRenderer<?> renderer = axisRenderers.get(axis);
	    double tickness = renderer.getTickness();
	    renderer.renderTo(this, plottingArea.getMinX() + yAxesThickness, position,
		    plottingArea.getWidth() - yAxesThickness - altYAxesThickness, tickness);
	    position += tickness;
	}
	position = plottingArea.getMinX();
	for (Axis<?> axis : yAxes) {
	    AxisRenderer<?> renderer = axisRenderers.get(axis);
	    double tickness = renderer.getTickness();
	    renderer.renderTo(this, position, plottingArea.getMinY() + altXAxesThickness, tickness,
		    plottingArea.getHeight() - xAxesThickness - altXAxesThickness);
	    position += tickness;
	}
	position = plottingArea.getMinY();
	for (Axis<?> axis : altXAxes) {
	    AxisRenderer<?> renderer = axisRenderers.get(axis);
	    double tickness = renderer.getTickness();
	    renderer.renderTo(this, plottingArea.getMinX() + yAxesThickness, position,
		    plottingArea.getWidth() - yAxesThickness - altYAxesThickness, tickness);
	    position += tickness;
	}
	position = (plottingArea.getMinX() + plottingArea.getWidth()) - altYAxesThickness;
	for (Axis<?> axis : altYAxes) {
	    AxisRenderer<?> renderer = axisRenderers.get(axis);
	    double tickness = renderer.getTickness();
	    renderer.renderTo(this, position, plottingArea.getMinY() + altXAxesThickness, tickness,
		    plottingArea.getHeight() - xAxesThickness - altXAxesThickness);
	    position += tickness;
	}

	return new Rectangle2D( //
		plottingArea.getMinX() + yAxesThickness, //
		plottingArea.getMinY() + altXAxesThickness, //
		plottingArea.getWidth() - yAxesThickness - altYAxesThickness, //
		plottingArea.getHeight() - xAxesThickness - altXAxesThickness //
	);
    }

    private void drawPlots(Rectangle2D plottingArea) {
	plotRenderers.clear();
	for (Plot<?, ?, ?> plot : plots) {
	    if (plot.hasData()) {
		PlotRenderer<?, ?, ?, ?, ?> plotRenderer = ((AbstractPlot<?, ?, ?>) plot)
			.getGenericRenderer(axisRenderers.get(plot.getXAxis()), axisRenderers.get(plot.getYAxis()));
		plotRenderers.put(plot, plotRenderer);

		plotRenderer.renderTo(this, plottingArea.getMinX(), plottingArea.getMinY(), plottingArea.getWidth(),
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
