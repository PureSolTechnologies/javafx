package com.puresoltechnologies.javafx.charts.plots.ohlc;

import java.time.Instant;

public class OHLCValue<T extends Number & Comparable<T>> implements Comparable<OHLCValue<T>> {

    private final Instant start;
    private final Instant end;
    private final T open;
    private final T high;
    private final T low;
    private final T close;
    private final boolean increase;

    public OHLCValue(Instant start, Instant end, T open, T high, T low, T close) {
	super();
	this.start = start;
	this.end = end;
	this.open = open;
	this.high = high;
	this.low = low;
	this.close = close;
	this.increase = close.doubleValue() > open.doubleValue() ? true : false;
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

    public final boolean isIncrease() {
	return increase;
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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = (prime * result) + ((close == null) ? 0 : close.hashCode());
	result = (prime * result) + ((end == null) ? 0 : end.hashCode());
	result = (prime * result) + ((high == null) ? 0 : high.hashCode());
	result = (prime * result) + ((low == null) ? 0 : low.hashCode());
	result = (prime * result) + ((open == null) ? 0 : open.hashCode());
	result = (prime * result) + ((start == null) ? 0 : start.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	OHLCValue<?> other = (OHLCValue<?>) obj;
	if (close == null) {
	    if (other.close != null) {
		return false;
	    }
	} else if (!close.equals(other.close)) {
	    return false;
	}
	if (end == null) {
	    if (other.end != null) {
		return false;
	    }
	} else if (!end.equals(other.end)) {
	    return false;
	}
	if (high == null) {
	    if (other.high != null) {
		return false;
	    }
	} else if (!high.equals(other.high)) {
	    return false;
	}
	if (low == null) {
	    if (other.low != null) {
		return false;
	    }
	} else if (!low.equals(other.low)) {
	    return false;
	}
	if (open == null) {
	    if (other.open != null) {
		return false;
	    }
	} else if (!open.equals(other.open)) {
	    return false;
	}
	if (start == null) {
	    if (other.start != null) {
		return false;
	    }
	} else if (!start.equals(other.start)) {
	    return false;
	}
	return true;
    }

}
