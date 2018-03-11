package com.puresoltechnologies.javafx.charts.plots;

/**
 * This is a generic, single data point for a plot.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public interface PlotDate<X, Y> {

    public X getX();

    public Y getY();

}
