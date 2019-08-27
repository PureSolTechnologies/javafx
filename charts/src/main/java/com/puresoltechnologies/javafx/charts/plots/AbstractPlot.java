package com.puresoltechnologies.javafx.charts.plots;

import java.util.Collections;
import java.util.List;

import com.puresoltechnologies.javafx.charts.axes.Axis;
import com.puresoltechnologies.javafx.charts.axes.AxisRenderer;
import com.puresoltechnologies.javafx.charts.axes.AxisType;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

public abstract class AbstractPlot<X extends Comparable<X>, Y extends Comparable<Y>, D> implements Plot<X, Y, D> {

    private final StringProperty title = new SimpleStringProperty();
    private final ObjectProperty<Color> color = new SimpleObjectProperty<>();
    private final ObservableList<D> data = FXCollections.observableArrayList();
    private final Axis<X> xAxis;
    private final Axis<Y> yAxis;
    private X minX = null;
    private X maxX = null;
    private Y minY = null;
    private Y maxY = null;

    public AbstractPlot(Axis<X> xAxis, Axis<Y> yAxis) {
	super();
	this.xAxis = xAxis;
	if (xAxis.getAxisType() != AxisType.X //
		&& xAxis.getAxisType() != AxisType.ALT_X) {
	    throw new IllegalArgumentException("Provided X axis is not an X axis.");
	}
	this.yAxis = yAxis;
	if (yAxis.getAxisType() != AxisType.Y //
		&& yAxis.getAxisType() != AxisType.ALT_Y) {
	    throw new IllegalArgumentException("Provided Y axis is not an Y axis.");
	}
	data.addListener((ListChangeListener<D>) change -> updateExtrema());
    }

    public AbstractPlot(String title, Axis<X> xAxis, Axis<Y> yAxis) {
	this(xAxis, yAxis);
	this.title.set(title);
    }

    public AbstractPlot(String title, Axis<X> xAxis, Axis<Y> yAxis, List<D> data) {
	this(title, xAxis, yAxis);
	setData(data);
    }

    protected abstract PlotRenderer<X, Y, D, ? extends AxisRenderer<X>, ? extends AxisRenderer<Y>> getRenderer(
	    AxisRenderer<X> xAxisRenderer, AxisRenderer<Y> yAxisRenderer);

    @SuppressWarnings("unchecked")
    protected final PlotRenderer<X, Y, D, ? extends AxisRenderer<X>, ? extends AxisRenderer<Y>> getGenericRenderer(
	    AxisRenderer<?> xAxisRenderer, AxisRenderer<?> yAxisRenderer) {
	return getRenderer((AxisRenderer<X>) xAxisRenderer, (AxisRenderer<Y>) yAxisRenderer);
    }

    @Override
    public final String getTitle() {
	return title.get();
    }

    @Override
    public final void setTitle(String title) {
	this.title.set(title);
    }

    @Override
    public final StringProperty titleProperty() {
	return title;
    }

    @Override
    public final ObjectProperty<Color> colorProperty() {
	return color;
    }

    @Override
    public final void setColor(Color color) {
	this.color.setValue(color);
    }

    @Override
    public final Color getColor() {
	return color.getValue();
    }

    @Override
    public final Axis<X> getXAxis() {
	return xAxis;
    }

    @Override
    public final Axis<Y> getYAxis() {
	return yAxis;
    }

    public abstract X getAxisX(D date);

    public abstract Y getAxisY(D date);

    private final void updateExtrema() {
	setMinX(null);
	setMaxX(null);
	setMinY(null);
	setMaxY(null);
	getData().forEach(value -> {
	    X x = getAxisX(value);
	    Y y = getAxisY(value);
	    updateMinX(x);
	    updateMaxX(x);
	    updateMaxY(y);
	    updateMinY(y);
	});
    }

    @Override
    public final boolean hasData() {
	return data.size() > 0 //
		&& getMinX() != null //
		&& getMaxX() != null //
		&& getMinY() != null //
		&& getMaxY() != null;
    }

    @Override
    public final X getMinX() {
	return minX;
    }

    @Override
    public final X getMaxX() {
	return maxX;
    }

    @Override
    public final Y getMinY() {
	return minY;
    }

    @Override
    public final Y getMaxY() {
	return maxY;
    }

    protected final void setMinX(X minX) {
	this.minX = minX;
    }

    protected final void setMaxX(X maxX) {
	this.maxX = maxX;
    }

    protected final void setMinY(Y minY) {
	this.minY = minY;
    }

    protected final void setMaxY(Y maxY) {
	this.maxY = maxY;
    }

    protected final void updateMinX(X value) {
	if (minX == null || minX.compareTo(value) > 0.0) {
	    minX = value;
	}
    }

    protected final void updateMaxX(X value) {
	if (maxX == null || maxX.compareTo(value) < 0.0) {
	    maxX = value;
	}
    }

    protected final void updateMinY(Y value) {
	if (minY == null || minY.compareTo(value) > 0.0) {
	    minY = value;
	}
    }

    protected final void updateMaxY(Y value) {
	if (maxY == null || maxY.compareTo(value) < 0.0) {
	    maxY = value;
	}
    }

    @Override
    public final ObservableList<D> data() {
	return data;
    }

    @Override
    public final List<D> getData() {
	return Collections.unmodifiableList(data);
    }

    @Override
    public final void setData(List<D> data) {
	this.data.setAll(data);
    }

}
