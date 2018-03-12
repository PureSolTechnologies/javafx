package com.puresoltechnologies.javafx.charts.plots.ohlc;

import java.time.Instant;

public class OHLCValue<T extends Number & Comparable<T>> implements Comparable<OHLCValue<T>> {

    private final Instant start;
    private final Instant end;
    private final T open;
    private final T high;
    private final T low;
    private final T close;

    public OHLCValue(Instant start, Instant end, T open, T high, T low, T close) {
	super();
	this.start = start;
	this.end = end;
	this.open = open;
	this.high = high;
	this.low = low;
	this.close = close;
    }

    public final Instant getStart() {
	return start;
    }

    public final Instant getEnd() {
	return end;
    }

    public final T getOpen() {
	return open;
    }

    public final T getHigh() {
	return high;
    }

    public final T getLow() {
	return low;
    }

    public final T getClose() {
	return close;
    }

    @Override
    public int compareTo(OHLCValue<T> other) {
	int result = this.close.compareTo(other.close);
	if (result != 0) {
	    return result;
	}
	result = this.open.compareTo(other.open);
	if (result != 0) {
	    return result;
	}
	result = this.high.compareTo(other.high);
	if (result != 0) {
	    return result;
	}
	result = this.low.compareTo(other.low);
	if (result != 0) {
	    return result;
	}
	return this.start.compareTo(other.start);
    }

}
