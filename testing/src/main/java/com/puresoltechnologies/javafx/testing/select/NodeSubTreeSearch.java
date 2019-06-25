package com.puresoltechnologies.javafx.testing.select;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.scene.Node;
import javafx.scene.Parent;

public interface NodeSubTreeSearch<T extends Node> extends NodeSearch {

    T getNode();

    @Override
    default List<Node> findNodes(Predicate<Node> filter) {
	T parent = getNode();
	if (Parent.class.isAssignableFrom(parent.getClass())) {
	    List<Node> nodes = new ArrayList<>();
	    addAllChildren((Parent) parent, nodes, filter);
	    return nodes;
	} else {
	    return null;
	}
    }

    private void addAllChildren(Parent parent, List<Node> nodes, Predicate<Node> filter) {
	System.out.println(parent.getClass().getName());
	for (Node node : parent.getChildrenUnmodifiable()) {
	    if (filter.test(node)) {
		nodes.add(node);
	    }
	    if (Parent.class.isAssignableFrom(node.getClass())) {
		addAllChildren((Parent) node, nodes, filter);
	    }
	}
    }

}
