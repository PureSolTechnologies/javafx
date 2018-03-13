package com.puresoltechnologies.javafx.charts.axes;

public class NumberAxis<T extends Number & Comparable<T>> extends OrdinalAxis<T> {

    public NumberAxis(String title, String unit, AxisType type, Class<T> valueType) {
	super(title, unit, type, valueType);
    }

}
