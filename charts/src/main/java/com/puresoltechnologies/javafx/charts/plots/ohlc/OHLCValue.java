package com.puresoltechnologies.javafx.charts.plots.ohlc;

import java.time.Instant;

public class OHLCValue<T extends Number> {

    private final Instant date;
    private final T high;
    private final T low;
    private final T open;
    private final T close;

    public OHLCValue(Instant date, T high, T low, T open, T close) {
	super();
	this.date = date;
	this.high = high;
	this.low = low;
	this.open = open;
	this.close = close;
    }

    public Instant getDate() {
	return date;
    }

    public T getHigh() {
	return high;
    }

    public T getLow() {
	return low;
    }

    public T getOpen() {
	return open;
    }

    public T getClose() {
	return close;
    }

}
