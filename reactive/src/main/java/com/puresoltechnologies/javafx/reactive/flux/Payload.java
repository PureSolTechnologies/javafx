package com.puresoltechnologies.javafx.reactive.flux;

import java.util.Objects;

/**
 * This class represents a payload to be dispatched.
 *
 * @author Rick-Rainer Ludwig
 *
 * @param <A> is the type of the action.
 * @param <D> is the type of data.
 */
public class Payload<A, D> {

    private final A action;
    private final D data;
    private final int hashCode;

    public Payload(A action, D data) {
	super();
	this.action = action;
	this.data = data;
	this.hashCode = Objects.hash(action, data);
    }

    public A getAction() {
	return action;
    }

    public D getData() {
	return data;
    }

    @Override
    public int hashCode() {
	return hashCode;
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
	Payload<?, ?> other = (Payload<?, ?>) obj;
	if (hashCode != other.hashCode) {
	    return false;
	}
	if (action == null) {
	    if (other.action != null) {
		return false;
	    }
	} else if (!action.equals(other.action)) {
	    return false;
	}
	if (data == null) {
	    if (other.data != null) {
		return false;
	    }
	} else if (!data.equals(other.data)) {
	    return false;
	}
	return true;
    }

}
