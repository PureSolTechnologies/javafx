package com.puresoltechnologies.javafx.charts.plots;

import com.puresoltechnologies.javafx.charts.axes.Axis;

import javafx.beans.property.ObjectProperty;

/**
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <X>
 *            is the data type of the X axis.
 * @param <Y>is
 *            the data type of the Y axis.
 * @param <D>
 *            is the actual data type of the data points to be plotted.
 */
public interface Plot<X, Y, D> {

    public String getTitle();

    public Axis<X> getXAxis();

    public Axis<Y> getYAxis();

    public ObjectProperty<PlotData<X, Y, D>> dataProperty();

    public PlotData<X, Y, D> getData();

    public void setData(PlotData<X, Y, D> data);

}
