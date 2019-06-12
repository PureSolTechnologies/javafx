package com.puresoltechnologies.javafx.testing.select;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public interface NodeSearch {

    default List<Node> findNodes(Predicate<Node> filter) {
	return Stage.getWindows().stream() //
		.map(window -> findNodesInScene(window.getScene(), filter)) //
		.flatMap(nodeList -> nodeList.stream()) //
		.collect(Collectors.toList());
    }

    default List<Node> findNodesInScene(Scene scene, Predicate<Node> filter) {
	List<Node> nodes = new ArrayList<>();
	Parent rootNode = scene.getRoot();
	addAllChildren(rootNode, nodes, filter);
	return nodes;
    }

    private void addAllChildren(Parent parent, List<Node> nodes, Predicate<Node> filter) {
	for (Node node : parent.getChildrenUnmodifiable()) {
	    if (filter.test(node)) {
		nodes.add(node);
	    }
	    if (Parent.class.isAssignableFrom(node.getClass())) {
		addAllChildren((Parent) node, nodes, filter);
	    }
	}
    }

    default Node findNode(Predicate<Node> filter) {
	List<Node> nodes = findNodes(filter);
	if (nodes.isEmpty()) {
	    return null;
	}
	if (nodes.size() > 1) {
	    throw new IllegalStateException("Multiple nodes were found.");
	}
	return nodes.get(0);
    }

    default Node findNodeById(String id) {
	return findNode(node -> id.equals(node.getId()));
    }

}
