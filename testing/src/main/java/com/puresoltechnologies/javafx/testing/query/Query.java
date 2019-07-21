package com.puresoltechnologies.javafx.testing.query;

import java.util.Iterator;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Labeled;

/**
 * This is the central interface for a {@link Node} query.
 *
 * @author Rick-Rainer Ludwig
 *
 * @param <N>
 */
public interface Query<N extends Node> extends Iterable<N> {

    Query<N> byId(String id);

    <L extends Labeled> Query<L> byText(String text);

    <R extends Node> Query<R> byType(Class<R> clazz);

    N getFirst();

    N getOne();

    List<N> getAll();

    @Override
    Iterator<N> iterator();
}
