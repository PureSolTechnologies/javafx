package com.puresoltechnologies.javafx.reactive;

import java.util.Objects;

/**
 * This interface is used to reference {@link FluxStore} topics. The topics
 * itself should be implemented as singletons. The preferred way is to great
 * enums implementing this interface. The constants are used as topics.
 * 
 * The main purpose to have this trivial interface and not a simple String is to
 * assure the topics are collected in a (or multiple) central place to enforce
 * reuse. Strings can contain typos and my not be found during refactorings.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public class Topic<T> {

    private final String id;
    private final Class<T> type;
    private final int hash;

    public Topic(String id, Class<T> type) {
	super();
	this.id = id;
	this.type = type;
	this.hash = Objects.hash(id, type);
    }

    public String getId() {
	return id;
    }

    public Class<T> getType() {
	return type;
    }

    @Override
    public int hashCode() {
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Topic<?> other = (Topic<?>) obj;
	if (hash != other.hash) {
	    return false;
	}
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (type == null) {
	    if (other.type != null)
		return false;
	} else if (!type.equals(other.type))
	    return false;
	return true;
    }

}
