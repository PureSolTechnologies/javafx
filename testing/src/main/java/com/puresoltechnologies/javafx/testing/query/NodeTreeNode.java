package com.puresoltechnologies.javafx.testing.query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.puresoltechnologies.graphs.trees.TreeLink;
import com.puresoltechnologies.graphs.trees.TreeNode;

import javafx.scene.Node;
import javafx.scene.Parent;

public class NodeTreeNode implements TreeNode<NodeTreeNode> {

    private final Node node;

    public NodeTreeNode(Node node) {
	super();
	this.node = node;
    }

    public Node getNode() {
	return node;
    }

    @Override
    public Set<TreeLink<NodeTreeNode>> getEdges() {
	Set<TreeLink<NodeTreeNode>> edges = new HashSet<>();
	if (Parent.class.isAssignableFrom(node.getClass())) {
	    if (node.getParent() != null) {
		edges.add(new TreeLink<>(new NodeTreeNode(node.getParent()), this));
	    }
	    for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
		edges.add(new TreeLink<>(this, new NodeTreeNode(child)));
	    }
	}
	return edges;
    }

    @Override
    public NodeTreeNode getParent() {
	return new NodeTreeNode(node.getParent());
    }

    @Override
    public boolean hasChildren() {
	if (!Parent.class.isAssignableFrom(node.getClass())) {
	    return false;
	}
	Parent parent = (Parent) node;
	return !parent.getChildrenUnmodifiable().isEmpty();
    }

    @Override
    public List<NodeTreeNode> getChildren() {
	List<NodeTreeNode> children = new ArrayList<>();
	if (Parent.class.isAssignableFrom(node.getClass())) {
	    for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
		children.add(new NodeTreeNode(child));
	    }
	}
	return children;
    }

    @Override
    public String getName() {
	return node.getId();
    }

}
