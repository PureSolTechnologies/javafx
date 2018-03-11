package com.puresoltechnologies.javafx.charts.plots;

public interface Plot<X, Y> {

    public String getTitle();

    public Axis<X> getXAxis();

    public Axis<Y> getYAxis();

}
