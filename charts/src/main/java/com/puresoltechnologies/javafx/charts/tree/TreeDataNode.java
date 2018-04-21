package com.puresoltechnologies.javafx.charts.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.puresoltechnologies.graphs.trees.TreeLink;
import com.puresoltechnologies.graphs.trees.TreeNode;

public class TreeDataNode implements TreeNode<TreeDataNode> {

    private final List<TreeDataNode> children = new ArrayList<>();
    private final TreeDataNode parent;
    private final String name;
    private final double value;

    public TreeDataNode(String name, double value) {
	super();
	this.parent = null;
	this.name = name;
	this.value = value;
    }

    public TreeDataNode(TreeDataNode parent, String name, double value) {
	super();
	this.parent = parent;
	this.name = name;
	this.value = value;
    }

    @Override
    public String getName() {
	return name;
    }

    public double getValue() {
	return value;
    }

    @Override
    public Set<TreeLink<TreeDataNode>> getEdges() {
	Set<TreeLink<TreeDataNode>> edges = new HashSet<>();
	children.forEach(child -> edges.add(new TreeLink<TreeDataNode>(this, child)));
	return edges;
    }

    @Override
    public TreeDataNode getParent() {
	return parent;
    }

    @Override
    public boolean hasChildren() {
	return !children.isEmpty();
    }

    @Override
    public List<TreeDataNode> getChildren() {
	return children;
    }

}
