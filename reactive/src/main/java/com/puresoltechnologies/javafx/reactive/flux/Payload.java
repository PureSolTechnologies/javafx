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
public class Payload {

    private final Enum<?> action;
    private final Object data;
    private final int hashCode;

    public Payload(Enum<?> action, Object data) {
	super();
	this.action = action;
	this.data = data;
	this.hashCode = Objects.hash(action, data);
    }

    public boolean hasActionOf(Class<? extends Enum<?>> actions) {
	return action.getClass().isAssignableFrom(actions);
    }

    public boolean hasDataOf(Class<?> type) {
	return data.getClass().isAssignableFrom(type);
    }

    public <Action extends Enum<Action>> Action getAction() {
	@SuppressWarnings("unchecked")
	Action action = (Action) this.action;
	return action;
    }

    public <Data> Data getData() {
	@SuppressWarnings("unchecked")
	Data data = (Data) this.data;
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
	Payload other = (Payload) obj;
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
	if (hashCode != other.hashCode) {
	    return false;
	}
	return true;
    }

}
