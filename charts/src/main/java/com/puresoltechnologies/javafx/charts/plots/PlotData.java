package com.puresoltechnologies.javafx.charts.plots;

import java.util.List;

import javafx.beans.property.ObjectProperty;

/**
 * This is the model interface for the plot data.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <X>
 * @param <Y>
 */
public interface PlotData<X extends Comparable<X>, Y extends Comparable<Y>, D> {

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

    public ObjectProperty<List<D>> dataProperty();

}
