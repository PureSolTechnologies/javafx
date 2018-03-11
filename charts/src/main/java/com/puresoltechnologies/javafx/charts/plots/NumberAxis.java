package com.puresoltechnologies.javafx.charts.plots;

public class NumberAxis<T extends Number> extends AbstractAxis<T> {

    public NumberAxis(String title, String unit, AxisType type, Class<T> valueType) {
	super(title, unit, type, valueType);
    }

}