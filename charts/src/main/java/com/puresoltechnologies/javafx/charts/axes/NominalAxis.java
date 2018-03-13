package com.puresoltechnologies.javafx.charts.axes;

public class NominalAxis<T> extends AbstractAxis<T> {

    public NominalAxis(String title, String unit, AxisType type, Class<T> valueType) {
	super(title, unit, type, valueType);
    }

}
