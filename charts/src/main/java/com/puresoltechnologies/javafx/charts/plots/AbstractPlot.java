package com.puresoltechnologies.javafx.charts.plots;

import java.util.Collections;
import java.util.List;

import com.puresoltechnologies.javafx.charts.axes.Axis;
import com.puresoltechnologies.javafx.charts.axes.AxisType;
import com.puresoltechnologies.javafx.charts.renderer.axes.AxisRenderer;
import com.puresoltechnologies.javafx.charts.renderer.plots.PlotRenderer;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.canvas.Canvas;

public abstract class AbstractPlot<X extends Comparable<X>, Y extends Comparable<Y>, D> implements Plot<X, Y, D> {

    private final StringProperty title = new SimpleStringProperty();
    private final ListProperty<D> data = new SimpleListProperty<>();
    private final Axis<X> xAxis;
    private final Axis<Y> yAxis;
    private X minX = null;
    private X maxX = null;
    private Y minY = null;
    private Y maxY = null;

    public AbstractPlot(Axis<X> xAxis, Axis<Y> yAxis) {
	super();
	this.xAxis = xAxis;
	if ((xAxis.getAxisType() != AxisType.X) //
		&& (xAxis.getAxisType() != AxisType.ALT_X)) {
	    throw new IllegalArgumentException("Provided X axis is not an X axis.");
	}
	this.yAxis = yAxis;
	if ((yAxis.getAxisType() != AxisType.Y) //
		&& (yAxis.getAxisType() != AxisType.ALT_Y)) {
	    throw new IllegalArgumentException("Provided Y axis is not an Y axis.");
	}
	data.addListener(new ListChangeListener<>() {
	    @Override
	    public void onChanged(Change<? extends D> change) {
		updateExtrema();
	    }
	});
    }

    public AbstractPlot(String title, Axis<X> xAxis, Axis<Y> yAxis) {
	this(xAxis, yAxis);
	this.title.set(title);
    }

    public AbstractPlot(String title, Axis<X> xAxis, Axis<Y> yAxis, List<D> data) {
	this(title, xAxis, yAxis);
	setData(data);
    }

    public abstract PlotRenderer getRenderer(Canvas canvas, AxisRenderer<X> xAxisRenderer,
	    AxisRenderer<Y> yAxisRenderer);

    @SuppressWarnings("unchecked")
    public PlotRenderer getGenericRenderer(PlotCanvas canvas, AxisRenderer<?> xAxisRenderer,
	    AxisRenderer<?> yAxisRenderer) {
	return getRenderer(canvas, (AxisRenderer<X>) xAxisRenderer, (AxisRenderer<Y>) yAxisRenderer);
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
    public final Axis<X> getXAxis() {
	return xAxis;
    }

    @Override
    public final Axis<Y> getYAxis() {
	return yAxis;
    }

    public abstract X getAxisX(D date);

    public abstract Y getAxisY(D date);

    protected abstract void updateExtrema();

    @Override
    public final boolean hasData() {
	return (data.get() != null) //
		&& (data.get().size() > 0) //
		&& (getMinX() != null) //
		&& (getMaxX() != null) //
		&& (getMinY() != null) //
		&& (getMaxY() != null);
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
	if ((minX == null) || (minX.compareTo(value) > 0.0)) {
	    minX = value;
	}
    }

    protected final void updateMaxX(X value) {
	if ((maxX == null) || (maxX.compareTo(value) < 0.0)) {
	    maxX = value;
	}
    }

    protected final void updateMinY(Y value) {
	if ((minY == null) || (minY.compareTo(value) > 0.0)) {
	    minY = value;
	}
    }

    protected final void updateMaxY(Y value) {
	if ((maxY == null) || (maxY.compareTo(value) < 0.0)) {
	    maxY = value;
	}
    }

    @Override
    public final List<D> getData() {
	List<D> list = data.get();
	if (list != null) {
	    return Collections.unmodifiableList(list);
	} else {
	    return null;
	}
    }

    @Override
    public final void setData(List<D> data) {
	this.data.set(FXCollections.observableList(data));
    }

    @Override
    public final ListProperty<D> dataProperty() {
	return data;
    }

}
