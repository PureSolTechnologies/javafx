package com.puresoltechnologies.javafx.reactive.flux;

/**
 * This is the interface for stores. The update of its clients is not done via
 * subscriptions and streams, but read-only properties.
 *
 * @author Rick-Rainer Ludwig
 *
 * @param <A>
 * @param <D>
 */
public interface Store<A extends Enum<A>, D> {

}
