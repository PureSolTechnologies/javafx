package com.puresoltechnologies.javafx.testing.select;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.Parent;

public interface NodeSearch<N extends Node> {

    N getNode();

    default List<Node> findAllNodes() {
	N rootNode = getNode();
	if (Parent.class.isAssignableFrom(rootNode.getClass())) {
	    List<Node> nodes = new ArrayList<>();
	    addAllChildren((Parent) rootNode, nodes);
	    return nodes;
	} else {
	    throw new IllegalStateException("Only javafx.scene.Parent nodes can have children.");
	}
    }

    private void addAllChildren(Parent parent, List<Node> nodes) {
	for (Node node : parent.getChildrenUnmodifiable()) {
	    nodes.add(node);
	    if (node instanceof Parent) {
		addAllChildren((Parent) node, nodes);
	    }
	}
    }

    default List<Node> findNodes(NodeFilter<Node> filter) {
	N rootNode = getNode();
	if (Parent.class.isAssignableFrom(rootNode.getClass())) {
	    List<Node> nodes = new ArrayList<>();
	    addAllChildren((Parent) rootNode, nodes, filter);
	    return nodes;
	} else {
	    throw new IllegalStateException("Only javafx.scene.Parent nodes can have children.");
	}
    }

    private void addAllChildren(Parent parent, List<Node> nodes, NodeFilter<Node> filter) {
	for (Node node : parent.getChildrenUnmodifiable()) {
	    if (filter.keep(node)) {
		nodes.add(node);
	    }
	    if (node instanceof Parent) {
		addAllChildren((Parent) node, nodes, filter);
	    }
	}
    }

    default Node findNode(NodeFilter<Node> filter) {
	N rootNode = getNode();
	if (Parent.class.isAssignableFrom(rootNode.getClass())) {
	    return findNode((Parent) rootNode, filter);
	} else {
	    throw new IllegalStateException("Only javafx.scene.Parent nodes can have children.");
	}
    }

    private Node findNode(Parent parent, NodeFilter<Node> filter) {
	for (Node node : parent.getChildrenUnmodifiable()) {
	    if (filter.keep(node)) {
		return node;
	    }
	    if (node instanceof Parent) {
		Node foundNode = findNode((Parent) node, filter);
		if (foundNode != null) {
		    return foundNode;
		}
	    }
	}
	return null;
    }

    default Node findNodeById(String id) {
	N rootNode = getNode();
	if (Parent.class.isAssignableFrom(rootNode.getClass())) {
	    return findNode((Parent) rootNode, node -> id.equals(node.getId()));
	} else {
	    throw new IllegalStateException("Only javafx.scene.Parent nodes can have children.");
	}
    }

    default <S extends Node> S findNode(Class<S> clazz, NodeFilter<S> filter) {
	N rootNode = getNode();
	if (Parent.class.isAssignableFrom(rootNode.getClass())) {
	    return findNode((Parent) rootNode, clazz, filter);
	} else {
	    throw new IllegalStateException("Only javafx.scene.Parent nodes can have children.");
	}
    }

    private <S extends Node> S findNode(Parent parent, Class<S> clazz, NodeFilter<S> filter) {
	for (Node node : parent.getChildrenUnmodifiable()) {
	    if (clazz.equals(node.getClass())) {
		@SuppressWarnings("unchecked")
		S s = (S) node;
		if (filter.keep(s)) {
		    return s;
		}
	    }
	    if (node instanceof Parent) {
		S foundNode = findNode((Parent) node, clazz, filter);
		if (foundNode != null) {
		    return foundNode;
		}
	    }
	}
	return null;
    }

    default <S extends Node> S findNodeById(Class<S> clazz, String id) {
	N rootNode = getNode();
	if (Parent.class.isAssignableFrom(rootNode.getClass())) {
	    return findNode((Parent) rootNode, clazz, node -> id.equals(node.getId()));
	} else {
	    throw new IllegalStateException("Only javafx.scene.Parent nodes can have children.");
	}
    }

}
