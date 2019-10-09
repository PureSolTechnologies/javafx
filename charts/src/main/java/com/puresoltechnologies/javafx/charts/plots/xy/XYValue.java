package com.puresoltechnologies.javafx.charts.plots.xy;

import java.text.NumberFormat;
import java.util.Locale;

import com.puresoltechnologies.javafx.charts.plots.PlotDatum;

public class XYValue<X extends Number & Comparable<X>, Y extends Number & Comparable<Y>> implements PlotDatum<X, Y> {

    private final X x;
    private final Y y;

    public XYValue(X x, Y y) {
	super();
	this.x = x;
	this.y = y;
    }

    public X getX() {
	return x;
    }

    public Y getY() {
	return y;
    }

    @Override
    public String getClipboardString(Locale locale) {
	NumberFormat numberFormat = NumberFormat.getInstance(locale);
	return numberFormat.format(x) + "\t" + numberFormat.format(y);
    }

    @Override
    public String toString() {
	return x + "," + y;
    }
}
