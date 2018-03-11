package com.puresoltechnologies.javafx.charts.plots;

public class OrdinalAxis<T extends Comparable<T>> extends AbstractAxis<T> {

    public OrdinalAxis(String title, String unit, AxisType type, Class<T> valueType) {
	super(title, unit, type, valueType);
    }

}
