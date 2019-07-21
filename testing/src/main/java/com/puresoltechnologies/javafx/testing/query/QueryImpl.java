package com.puresoltechnologies.javafx.testing.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import com.puresoltechnologies.graphs.trees.TreeIterator;
import com.puresoltechnologies.streaming.iterators.ConvertingStreamIterator;
import com.puresoltechnologies.streaming.iterators.FilteringStreamIterator;

import javafx.scene.Node;
import javafx.scene.control.Labeled;

/**
 * Implementation for {@link Query}.
 *
 * @author Rick-Rainer Ludwig
 *
 */
class QueryImpl<N extends Node> implements Query<N> {

    private final Predicate<N> predicate;

    QueryImpl(Predicate<N> predicate) {
	this.predicate = predicate;
    }

    @Override
    public Query<N> byId(String id) {
	return new QueryImpl<>(predicate.and(node -> id.equals(node.getId())));
    }

    @Override
    public <L extends Labeled> Query<L> byText(String text) {
	Predicate<N> textCheck = node -> Labeled.class.isAssignableFrom(node.getClass()) //
		&& (text.equals(((Labeled) node).getText()));
	@SuppressWarnings("unchecked")
	Predicate<L> newPredicate = (Predicate<L>) predicate //
		.and(textCheck);
	return new QueryImpl<>(newPredicate);
    }

    @Override
    public <R extends Node> Query<R> byType(Class<R> clazz) {
	Predicate<N> typeCheck = node -> clazz.isAssignableFrom(node.getClass());
	@SuppressWarnings("unchecked")
	Predicate<R> newPredicate = (Predicate<R>) predicate //
		.and(typeCheck);
	return new QueryImpl<>(newPredicate);
    }

    @Override
    public N getFirst() {
	Iterator<N> iterator = iterator();
	if (iterator.hasNext()) {
	    return iterator.next();
	}
	return null;
    }

    @Override
    public N getOne() {
	Iterator<N> iterator = iterator();
	if (!iterator.hasNext()) {
	    return null;
	}
	N result = iterator.next();
	if (iterator.hasNext()) {
	    throw new IllegalStateException("Multiple results were found.");
	}
	return result;
    }

    @Override
    public List<N> getAll() {
	List<N> results = new ArrayList<>();
	iterator().forEachRemaining(node -> results.add(node));
	return results;
    }

    @Override
    public Iterator<N> iterator() {
	TreeIterator<NodeTreeNode> treeIterator = new TreeIterator<>(new NodeTreeNode(null));
	@SuppressWarnings("unchecked")
	ConvertingStreamIterator<NodeTreeNode, N> converter = new ConvertingStreamIterator<>(treeIterator,
		node -> (N) node.getNode());
	FilteringStreamIterator<N> filter = new FilteringStreamIterator<>(converter, predicate);
	return filter;
    }

}
