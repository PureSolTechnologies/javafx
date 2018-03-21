package com.puresoltechnologies.javafx.charts.plots;

import java.util.List;

import com.puresoltechnologies.javafx.charts.axes.Axis;

import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;

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
public interface Plot<X extends Comparable<X>, Y extends Comparable<Y>, D> {

    public String getTitle();

    public void setTitle(String title);

    public StringProperty titleProperty();

    public Axis<X> getXAxis();

    public Axis<Y> getYAxis();

    public boolean hasData();

    public X getMinX();

    public X getMaxX();

    public Y getMinY();

    public Y getMaxY();

    /**
     * Returns the data to be plotted.
     * 
     * @return A {@link List} of values is returned.
     */
    public List<D> getData();

    /**
     * Returns the data to be plotted.
     */
    public void setData(List<D> data);

    public ListProperty<D> dataProperty();

}
