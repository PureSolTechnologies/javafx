package com.puresoltechnologies.javafx.charts.plots;

import java.util.List;

import com.puresoltechnologies.javafx.charts.axes.Axis;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This is an abstract class providing common functionality for
 * {@link PointBasedPlot}s.
 *
 * @author Rick-Rainer Ludwig
 *
 * @param <X> see {@link Plot}.
 * @param <Y> see {@link Plot}.
 * @param <D> see {@link Plot}.
 */
public abstract class AbstractPointBasedPlot<X extends Comparable<X>, Y extends Comparable<Y>, D>
	extends AbstractPlot<X, Y, D> implements PointBasedPlot<X, Y, D> {

    private final ObjectProperty<ConnectingLineStyle> connectingLineStyle = new SimpleObjectProperty<>(
	    ConnectingLineStyle.NONE);

    public AbstractPointBasedPlot(Axis<X> xAxis, Axis<Y> yAxis) {
	super(xAxis, yAxis);
    }

    public AbstractPointBasedPlot(String title, Axis<X> xAxis, Axis<Y> yAxis, List<D> data) {
	super(title, xAxis, yAxis, data);
    }

    public AbstractPointBasedPlot(String title, Axis<X> xAxis, Axis<Y> yAxis) {
	super(title, xAxis, yAxis);
    }

    public ObjectProperty<ConnectingLineStyle> connectingLineStyleProperty() {
	return connectingLineStyle;
    }

    public ConnectingLineStyle getConnectingLineStyle() {
	return connectingLineStyle.getValue();
    }

    public void setConnectingLineStyle(ConnectingLineStyle connectingLineStyle) {
	this.connectingLineStyle.setValue(connectingLineStyle);
    }

}
