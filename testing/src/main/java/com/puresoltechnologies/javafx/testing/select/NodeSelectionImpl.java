package com.puresoltechnologies.javafx.testing.select;

import javafx.scene.Node;

public class NodeSelectionImpl<N extends Node> implements NodeSelection<N> {

    private final N node;

    public NodeSelectionImpl(N node) {
	super();
	this.node = node;
    }

    @Override
    public N getNode() {
	return node;
    }

}
