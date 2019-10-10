package com.puresoltechnologies.javafx.charts.plots;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;

/**
 * A point based plot is a simple plot which shows its data as single mark
 * defined with X and Y coordinate. A non point-based plot could be bubble plots
 * or bar charts.
 *
 * @author Rick-Rainer Ludwig
 *
 * @param <X> see {@link Plot}.
 * @param <Y> see {@link Plot}.
 * @param <D> see {@link Plot}.
 */
public interface PointBasedPlot<X extends Comparable<X>, Y extends Comparable<Y>, D extends PlotDatum<X, Y>>
	extends Plot<X, Y, D> {

    ObjectProperty<MarkerType> markerTypeProperty();

    default MarkerType getMarkerType() {
	return markerTypeProperty().getValue();
    }

    default void setMarkerType(MarkerType markerType) {
	markerTypeProperty().setValue(markerType);
    }

    DoubleProperty markerSizeProperty();

    default double getMarkerSize() {
	return markerSizeProperty().getValue();
    }

    default void setMarkerSize(double markerSize) {
	markerSizeProperty().setValue(markerSize);
    }

    /**
     * Returns the {@link InterpolationType} property.
     *
     * @return
     */
    ObjectProperty<InterpolationType> interpolationTypeProperty();

    /**
     * Returns the {@link InterpolationType}.
     *
     * @return
     */
    default InterpolationType getInterpolationType() {
	return interpolationTypeProperty().getValue();
    }

    /**
     * Sets the {@link InterpolationType}.
     *
     * @param interpolationType is the {@link InterpolationType} to be set.
     */
    default void setInterpolationType(InterpolationType interpolationType) {
	interpolationTypeProperty().setValue(interpolationType);
    }

}
