package com.puresoltechnologies.javafx.charts.plots.xy;

public class XYValue<X extends Number & Comparable<X>, Y extends Number & Comparable<Y>> {

    private final X x;
    private final Y y;

    public XYValue(X x, Y y) {
	super();
	this.x = x;
	this.y = y;
    }

    public X getX() {
	return x;
    }

    public Y getY() {
	return y;
    }

}
