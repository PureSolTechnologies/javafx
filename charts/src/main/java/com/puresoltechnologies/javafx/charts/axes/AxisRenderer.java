package com.puresoltechnologies.javafx.charts.axes;

import com.puresoltechnologies.javafx.charts.Renderer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;

/**
 * General interface for axis renderer.
 *
 * @author Rick-Rainer Ludwig
 * @param <T> is the type of the axis values.
 */
public interface AxisRenderer<T> extends Renderer {

    Axis<T> getAxis();

    /**
     * Returns the property for the auto scale setting for the minimum value.
     *
     * If <code>true</code> is set, the minimum value is set automatically to the
     * minimum value of the plots attached.
     *
     * @return A {@link BooleanProperty} is returned containing the current setting.
     */
    BooleanProperty autoScaleMinProperty();

    /**
     * Sets the auto scale for the minimum value using
     * {@link #autoScaleMinProperty()}.
     *
     * @param autoScale defines whether the minimum value is auto scaled or not.
     */
    default void setAutoScaleMin(boolean autoScale) {
        autoScaleMinProperty().set(autoScale);
    }

    /**
     * This method is used to retrieve the current auto scale setting for the
     * minimum value.
     *
     * @return <code>true</code> is returned when the auto scale is switched on.
     *         <code>false</code> is returned otherwise.
     */
    default boolean getAutoScaleMin() {
        return autoScaleMinProperty().get();
    }

    /**
     * Returns the property for the auto scale setting for the maximum value.
     *
     * If <code>true</code> is set, the maximum value is set automatically to the
     * maximum value of the plots attached.
     *
     * @return A {@link BooleanProperty} is returned containing the current setting.
     */
    BooleanProperty autoScaleMaxProperty();

    /**
     * Sets the auto scale for the maximum value using
     * {@link #autoScaleMaxProperty()}.
     *
     * @param autoScale defines whether the maximum value is auto scaled or not.
     */
    default void setAutoScaleMax(boolean autoScale) {
        autoScaleMaxProperty().set(autoScale);
    }

    /**
     * This method is used to retrieve the current auto scale setting for the
     * maximum value.
     *
     * @return <code>true</code> is returned when the auto scale is switched on.
     *         <code>false</code> is returned otherwise.
     */
    default boolean getAutoScaleMax() {
        return autoScaleMaxProperty().get();
    }

    /**
     * Returns the minimum property property. It defines the cur
     *
     * @return
     */
    ObjectProperty<T> minProperty();

    ObjectProperty<T> maxProperty();

    default void setMin(T min) {
        minProperty().set(min);
    }

    /**
     * This method returns the axis minimum value.
     *
     * @return The minimum value is returned with the given type
     */
    default T getMin() {
        return minProperty().get();
    }

    default void setMax(T max) {
        maxProperty().set(max);
    }

    /**
     * This method returns the axis maximum value.
     *
     * @return The maximum value is returned with the given type
     */
    default T getMax() {
        return maxProperty().get();
    }

    default void scale(double factor) {
        scale(factor, 0.5);
    }

    void scale(double factor, double ratioMinToMax);

    /**
     * Returns the thickness of the rendered axis. For x axes, this is the actual
     * height and for y axes the width. The other dimension is specified by the
     * plot.
     *
     * @return The thickness of the axis.
     */
    double getTickness();

    /**
     * This method moves the axis values by a defined number of steps. A step is
     * simply the current accuracy.
     *
     * @param steps
     */
    void move(double fractionOfRange);

}