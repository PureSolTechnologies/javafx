package com.puresoltechnologies.javafx.charts.axes;

import com.puresoltechnologies.javafx.charts.AbstractRenderer;
import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public abstract class AbstractAxisRenderer<T, A extends Axis<T>> extends AbstractRenderer implements AxisRenderer<T> {

    protected static final double AXIS_THICKNESS = 10.0;
    protected static final double MIN_X_DISTANCE = 50.0;
    protected static final double MIN_Y_DISTANCE = 25.0;

    private final BooleanProperty autoScaleMin = new SimpleBooleanProperty(true);
    private final BooleanProperty autoScaleMax = new SimpleBooleanProperty(true);

    private final A axis;
    private final ObservableList<Plot<?, ?, ?>> plots;

    private final ObjectProperty<T> min = new SimpleObjectProperty<>(null);
    private final ObjectProperty<T> max = new SimpleObjectProperty<>(null);

    public AbstractAxisRenderer(A axis, ObservableList<Plot<?, ?, ?>> plots) {
        this.axis = axis;
        this.plots = plots;
        plots.addListener((ListChangeListener<Plot<?, ?, ?>>) c -> updateMinMax());
        plots.forEach(plot -> plot.data().addListener((ListChangeListener<Object>) arg0 -> updateMinMax()));
        updateMinMax();
        autoScaleMin.addListener((o, oldValue, newValue) -> {
            if (newValue) {
                updateMinMax();
            }
        });
        autoScaleMax.addListener((o, oldValue, newValue) -> {
            if (newValue) {
                updateMinMax();
            }
        });
    }

    @Override
    public BooleanProperty autoScaleMinProperty() {
        return autoScaleMin;
    }

    @Override
    public BooleanProperty autoScaleMaxProperty() {
        return autoScaleMax;
    }

    protected abstract void updateMinMax();

    @Override
    public ObjectProperty<T> minProperty() {
        return min;
    }

    @Override
    public ObjectProperty<T> maxProperty() {
        return max;
    }

    @Override
    public final A getAxis() {
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
        text.setFont(axis.getTitleFont().toFont());
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
    public void draw(Canvas canvas, double x, double y, double width, double height) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        clearAxisArea(gc, x, y, width, height);
        drawAxis(gc, x, y, width, height);
        if (hasData()) {
            drawTicks(gc, x, y, width, height);
        }
        drawAxisTitle(canvas, x, y, width, height);
    }

    private boolean hasData() {
        return (getMin() != null) && (getMax() != null);
    }

    private void clearAxisArea(GraphicsContext gc, double x, double y, double width, double height) {
        gc.setFill(axis.getBackgroundColor());
        gc.setStroke(axis.getBackgroundColor());
        gc.fillRect(x, y, width, height);
    }

    private void drawAxis(GraphicsContext gc, double x, double y, double width, double height) {
        gc.setFill(axis.getColor());
        gc.setStroke(axis.getColor());
        gc.setLineWidth(axis.getAxisWidth());
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

    private void drawAxisTitle(Canvas canvas, double x, double y, double width, double height) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // TITLE
        String axisTitle = axis.getTitle();
        if (axis.getUnit() != null) {
            axisTitle += " (" + axis.getUnit() + ")";
        }
        Text titleText = new Text(axisTitle);
        titleText.setFont(axis.getTitleFont().toFont());
        titleText.applyCss();
        // Set attributes
        gc.setStroke(axis.getTitleFont().getColor());
        gc.setFill(axis.getTitleFont().getColor());
        gc.setFont(axis.getTitleFont().toFont());
        gc.setTextAlign(TextAlignment.CENTER);

        switch (axis.getAxisType()) {
        case X:
            // Title
            gc.setTextBaseline(VPos.BOTTOM);
            gc.fillText(axisTitle, x + (width / 2.0), (y + height));
            break;
        case ALT_X:
            // Title
            gc.setTextBaseline(VPos.TOP);
            gc.fillText(axisTitle, x + (width / 2.0), y);
            break;
        case Y:
            // Title
            gc.setTextBaseline(VPos.TOP);
            gc.rotate(-90);
            gc.fillText(axisTitle, -canvas.getHeight() / 2.0, x);
            gc.rotate(90);
            break;
        case ALT_Y:
            // Title
            gc.setTextBaseline(VPos.BOTTOM);
            gc.rotate(-90);
            gc.fillText(axisTitle, -canvas.getHeight() / 2.0, x + width);
            gc.rotate(90);
            break;
        }
    }

}
