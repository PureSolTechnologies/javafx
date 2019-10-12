package com.puresoltechnologies.javafx.charts.plots;

import java.util.List;

import com.puresoltechnologies.javafx.charts.axes.Axis;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

/**
 *
 * @author Rick-Rainer Ludwig
 *
 * @param <X>   is the data type of the X axis.
 * @param <Y>is the data type of the Y axis.
 * @param <D>   is the actual data type of the data points to be plotted.
 */
public interface Plot<X extends Comparable<X>, Y extends Comparable<Y>, D extends PlotDatum<X, Y>> {

    StringProperty titleProperty();

    default String getTitle() {
	return titleProperty().get();
    }

    default void setTitle(String title) {
	titleProperty().set(title);
    }

    ObjectProperty<Color> colorProperty();

    default void setColor(Color color) {
	colorProperty().set(color);
    }

    default Color getColor() {
	return colorProperty().get();
    }

    BooleanProperty visibleProperty();

    default void setVisible(boolean visible) {
	visibleProperty().set(visible);
    }

    default boolean isVisible() {
	return visibleProperty().get();
    }

    Axis<X> getXAxis();

    Axis<Y> getYAxis();

    boolean hasData();

    X getMinX();

    X getMaxX();

    Y getMinY();

    Y getMaxY();

    ObservableList<D> data();

    /**
     * Returns the data to be plotted.
     *
     * @return A {@link List} of values is returned.
     */
    List<D> getData();

    /**
     * Returns the data to be plotted.
     */
    void setData(List<D> data);

}
