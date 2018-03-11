package com.puresoltechnologies.javafx.charts.plots;

import javafx.collections.ObservableList;

/**
 * This is the model interface for the plot data.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <X>
 * @param <Y>
 */
public interface PlotData<X, Y> {

    /**
     * Returns the data to be plotted.
     * 
     * @return
     */
    public ObservableList<PlotDate<X, Y>> getData();

}
