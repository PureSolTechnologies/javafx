package com.puresoltechnologies.javafx.charts.plots;

import javafx.beans.property.ObjectProperty;

/**
 *
 * @author Rick-Rainer Ludwig
 *
 * @param <X> see {@link Plot}.
 * @param <Y> see {@link Plot}.
 * @param <D> see {@link Plot}.
 */
public interface PointBasedPlot<X extends Comparable<X>, Y extends Comparable<Y>, D> extends Plot<X, Y, D> {

    /**
     * Returns the {@link ConnectingLineStyle} property.
     *
     * @return
     */
    ObjectProperty<ConnectingLineStyle> connectingLineStyleProperty();

    /**
     * Returns the {@link ConnectingLineStyle}.
     *
     * @return
     */
    ConnectingLineStyle getConnectingLineStyle();

    /**
     * Sets the {@link ConnectingLineStyle}.
     *
     * @param connectingLineStyle is the {@link ConnectingLineStyle} to be set.
     */
    void setConnectingLineStyle(ConnectingLineStyle connectingLineStyle);

}
