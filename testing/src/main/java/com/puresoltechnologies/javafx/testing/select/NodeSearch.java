package com.puresoltechnologies.javafx.testing.select;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import org.awaitility.Awaitility;

import javafx.scene.Node;
import javafx.scene.control.Labeled;

/**
 * This is the central interface for all classes which provide searching for
 * nodes.
 *
 * @author Rick-Rainer Ludwig
 */
public interface NodeSearch {

    List<Node> findNodes(Predicate<Node> filter);

    default Selection<Node> findNode(Predicate<Node> filter) {
	List<Node> nodes = findNodes(filter);
	if (nodes.isEmpty()) {
	    return null;
	}
	if (nodes.size() > 1) {
	    throw new IllegalStateException("Multiple nodes (" + nodes.size() + ") were found.");
	}
	return new Selection<>(nodes.get(0));
    }

    default Selection<Node> waitForNode(Predicate<Node> filter) {
	Selection<Node> node = Awaitility.await() //
		.pollDelay(100, TimeUnit.MILLISECONDS) //
		.atMost(10, TimeUnit.SECONDS) //
		.until(() -> {
		    try {
			return findNode(filter);
		    } catch (IllegalStateException e) {
			return null;
		    }
		}, n -> n != null);
	return node;
    }

    default <T extends Node> Selection<T> findNode(Class<T> clazz, Predicate<T> filter) {
	Predicate<Node> classPredicate = node -> clazz.isAssignableFrom(node.getClass());
	Predicate<Node> castedPredicate = node -> {
	    @SuppressWarnings("unchecked")
	    T t = (T) node;
	    return filter.test(t);
	};
	@SuppressWarnings("unchecked")
	Selection<T> selection = (Selection<T>) findNode(classPredicate.and(castedPredicate));
	return selection;
    }

    default <T extends Node> Selection<T> findNodeById(Class<T> clazz, String id) {
	return findNode(clazz, node -> id.equals(node.getId()));
    }

    default <T extends Labeled> Selection<T> findNodeByText(Class<T> clazz, String text) {
	return findNode(clazz, node -> text.equals(node.getText()));
    }

}
