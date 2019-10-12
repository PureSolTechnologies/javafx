package com.puresoltechnologies.javafx.charts.axes;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class OrdinalAxis<T extends Comparable<T>> extends NominalAxis<T> {

    private final ObjectProperty<T> min = new SimpleObjectProperty<>(null);
    private final ObjectProperty<T> max = new SimpleObjectProperty<>(null);

    public OrdinalAxis(String title, String unit, AxisType type, Class<T> valueType) {
	super(title, unit, type, valueType);
    }

    /**
     * This property is used to specify the minimum value of the axis to be shown.
     * If no values is specified (<code>null</code> is set), then auto-scaling is
     * set.
     *
     * @return An {@link ObjectProperty} is returned containing the setting.
     */
    public ObjectProperty<T> minProperty() {
	return min;
    }

    /**
     * This method is used to set the minimum value or auto-scaling for the minimum
     * value. See {@link #minProperty()} for details.
     *
     * @param min is the minimum value to be set.
     */
    public void setMin(T min) {
	this.min.setValue(min);
    }

    /**
     * This method returns the minimum value setting.
     *
     * @return The minimum value is returned.
     */
    public T getMin() {
	return min.getValue();
    }

    /**
     * This property is used to specify the maximum value of the axis to be shown.
     * If no values is specified (<code>null</code> is set), then auto-scaling is
     * set.
     *
     * @return An {@link ObjectProperty} is returned containing the setting.
     */
    public ObjectProperty<T> maxProperty() {
	return max;
    }

    /**
     * This method is used to set the maximum value or auto-scaling for the maximum
     * value. See {@link #maxProperty()} for details.
     *
     * @param max is the minimum value to be set.
     */
    public void setMax(T max) {
	this.max.setValue(max);
    }

    /**
     * This method returns the maximum value setting.
     *
     * @return The maximum value is returned.
     */
    public T getMax() {
	return max.getValue();
    }

}
