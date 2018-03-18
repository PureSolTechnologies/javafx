package com.puresoltechnologies.javafx.charts.plots;

import java.util.Collections;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public abstract class AbstractPlotData<X extends Comparable<X>, Y extends Comparable<Y>, D>
	implements PlotData<X, Y, D> {

    private final ObjectProperty<List<D>> data = new SimpleObjectProperty<>();
    private X minX = null;
    private X maxX = null;
    private Y minY = null;
    private Y maxY = null;

    protected AbstractPlotData() {
	data.addListener(list -> {
	    updateExtrema();
	});
    }

    protected abstract void updateExtrema();

    @Override
    public X getMinX() {
	return minX;
    }

    @Override
    public X getMaxX() {
	return maxX;
    }

    @Override
    public Y getMinY() {
	return minY;
    }

    @Override
    public Y getMaxY() {
	return maxY;
    }

    protected void setMinX(X minX) {
	this.minX = minX;
    }

    protected void setMaxX(X maxX) {
	this.maxX = maxX;
    }

    protected void setMinY(Y minY) {
	this.minY = minY;
    }

    protected void setMaxY(Y maxY) {
	this.maxY = maxY;
    }

    protected void updateMinX(X value) {
	if ((minX == null) || (minX.compareTo(value) > 0.0)) {
	    minX = value;
	}
    }

    protected void updateMaxX(X value) {
	if ((maxX == null) || (maxX.compareTo(value) < 0.0)) {
	    maxX = value;
	}
    }

    protected void updateMinY(Y value) {
	if ((minY == null) || (minY.compareTo(value) > 0.0)) {
	    minY = value;
	}
    }

    protected void updateMaxY(Y value) {
	if ((maxY == null) || (maxY.compareTo(value) < 0.0)) {
	    maxY = value;
	}
    }

    @Override
    public List<D> getData() {
	return Collections.unmodifiableList(data.get());
    }

    @Override
    public void setData(List<D> data) {
	this.data.set(data);
    }

    @Override
    public ObjectProperty<List<D>> dataProperty() {
	return data;
    }

}
