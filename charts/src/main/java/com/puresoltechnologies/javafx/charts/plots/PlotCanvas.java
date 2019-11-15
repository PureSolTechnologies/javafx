package com.puresoltechnologies.javafx.charts.plots;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puresoltechnologies.javafx.charts.axes.Axis;
import com.puresoltechnologies.javafx.charts.axes.AxisRenderer;
import com.puresoltechnologies.javafx.charts.axes.AxisRendererFactory;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.input.GestureEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

public class PlotCanvas extends Canvas {

    protected final ObjectProperty<Color> frameColor = new SimpleObjectProperty<>(Color.BLACK);
    protected final BooleanProperty visibleFrame = new SimpleBooleanProperty(true);
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
	frameColor.addListener(event -> draw());
	visibleFrame.addListener(event -> draw());
	backgroundColor.addListener(event -> draw());
	padding.addListener(event -> draw());

	plots.addListener((ListChangeListener<Object>) change -> {
	    reregisterAllPlots();
	    draw();
	});

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
	setOnScrollStarted(event -> {
	    if (event.isControlDown()) {
		setCursor(Cursor.CROSSHAIR);
	    } else {
		setCursor(Cursor.MOVE);
	    }
	});
	setOnScrollFinished(event -> {
	    setCursor(Cursor.DEFAULT);
	});
	setOnScroll(event -> {
	    double x = event.getX();
	    double y = event.getY();
	    if (event.isControlDown()) {
		int stepsY = (int) (event.getDeltaY() / event.getMultiplierY());
		double zoomFactor = Math.pow(0.9, stepsY);
		zoom(event, x, y, zoomFactor);
	    } else {
		move(event, x, y, event.getDeltaX(), event.getDeltaY());
	    }
	});
	setOnZoom(event -> {
	    System.err.println("zoom: factor=" + event.getTotalZoomFactor());
	    double x = event.getX();
	    double y = event.getY();
	    double zoomFactor = event.getZoomFactor();
	    zoom(event, x, y, zoomFactor);
	});

    }

    private void move(ScrollEvent event, double x, double y, double deltaX, double deltaY) {
	if ((plotArea != null) //
		&& (plotArea.contains(x, y)) //
	) {
	    move(x, y, deltaX, deltaY);
	    draw();
	    event.consume();
	} else {
	    boolean handled = false;
	    for (AxisRenderer<?> renderer : axisRenderers.values()) {
		if (renderer.getLocation().contains(x, y)) {
		    moveSingleAxis(x, y, deltaX, deltaY, renderer);
		    handled = true;
		}
	    }
	    if (handled) {
		draw();
		event.consume();
	    }
	}
    }

    private void move(double x, double y, double deltaX, double deltaY) {
	for (AxisRenderer<?> renderer : axisRenderers.values()) {
	    moveSingleAxis(x, y, deltaX, deltaY, renderer);
	}
    }

    private void moveSingleAxis(double x, double y, double deltaX, double deltaY, AxisRenderer<?> renderer) {
	Rectangle2D location = renderer.getLocation();
	switch (renderer.getAxis().getAxisType()) {
	case X:
	case ALT_X:
	    double size = location.getWidth();
	    renderer.move(deltaX / size);
	    break;
	case Y:
	case ALT_Y:
	    size = location.getHeight();
	    renderer.move(-deltaY / size);
	    break;
	}
    }

    private void zoom(GestureEvent event, double x, double y, double zoomFactor) {
	if ((plotArea != null) //
		&& (plotArea.contains(x, y)) //
	) {
	    zoom(x, y, zoomFactor);
	    draw();
	    event.consume();
	} else {
	    boolean handled = false;
	    for (AxisRenderer<?> renderer : axisRenderers.values()) {
		if (renderer.getLocation().contains(x, y)) {
		    zoomSingleAxis(x, y, zoomFactor, renderer);
		    handled = true;
		}
	    }
	    if (handled) {
		draw();
		event.consume();
	    }
	}
    }

    private void zoom(double x, double y, double zoomFactor) {
	for (AxisRenderer<?> renderer : axisRenderers.values()) {
	    zoomSingleAxis(x, y, zoomFactor, renderer);
	}
    }

    private void zoomSingleAxis(double x, double y, double zoomFactor, AxisRenderer<?> renderer) {
	Rectangle2D location = renderer.getLocation();
	double ratioMinToMax = 0.0;
	switch (renderer.getAxis().getAxisType()) {
	case X:
	case ALT_X:
	    double size = location.getWidth();
	    ratioMinToMax = (x - location.getMinX()) / size;
	    break;
	case Y:
	case ALT_Y:
	    size = location.getHeight();
	    ratioMinToMax = 1.0 - ((y - location.getMinY()) / size);
	    break;
	}
	renderer.scale(zoomFactor, ratioMinToMax);
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
	}
	draw();
    }

    public ObjectProperty<Color> frameColorProperty() {
	return frameColor;
    }

    public BooleanProperty frameVisibleProperty() {
	return visibleFrame;
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

	registerPlot(plot);

	plot.data().addListener((ListChangeListener<Object>) change -> draw());
	plot.visibleProperty().addListener((o, oldValue, newValue) -> draw());
	plot.colorProperty().addListener((o, oldValue, newValue) -> draw());
    }

    private void reregisterAllPlots() {
	plotRenderers.clear();
	xAxes.clear();
	yAxes.clear();
	altXAxes.clear();
	altYAxes.clear();
	axisRenderers.clear();
	affectedPlots.clear();
	plots.forEach(plot -> registerPlot(plot));
    }

    private void registerPlot(Plot<?, ?, ?> plot) {
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
	if (visibleFrame.get()) {
	    GraphicsContext gc = getGraphicsContext2D();
	    gc.setStroke(frameColor.get());
	    gc.strokeRect( //
		    0.0, //
		    0.0, //
		    getWidth(), //
		    getHeight() //
	    );
	}
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
	    if (plot.isVisible() && plot.hasData()) {
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
