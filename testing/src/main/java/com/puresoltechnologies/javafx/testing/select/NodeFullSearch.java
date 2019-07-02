package com.puresoltechnologies.javafx.testing.select;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Window;

public interface NodeFullSearch extends NodeSearch {

    @Override
    default List<Node> findNodes(Predicate<Node> filter) {
	return Window.getWindows().stream() //
		.map(window -> {
		    System.out.println("window: " + window.getClass().getName());
		    return findNodesInScene(window.getScene(), filter);
		}) //
		.flatMap(nodeList -> nodeList.stream()) //
		.collect(Collectors.toList());
    }

    default List<Node> findNodesInScene(Scene scene, Predicate<Node> filter) {
	List<Node> nodes = new ArrayList<>();
	Parent rootNode = scene.getRoot();
	if (filter.test(rootNode)) {
	    nodes.add(rootNode);
	}
	addAllChildren(rootNode, nodes, filter);
	return nodes;
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
