package com.puresoltechnologies.javafx.charts.plots;

import java.util.Locale;

/**
 * This interface is used to specify some essential functionality for plot data.
 *
 * @author Rick-Rainer Ludwig
 */
public interface PlotDatum<X, Y> {

    String getClipboardString(Locale locale);

}
