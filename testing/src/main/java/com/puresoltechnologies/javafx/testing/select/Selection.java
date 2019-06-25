package com.puresoltechnologies.javafx.testing.select;

import com.puresoltechnologies.javafx.testing.mouse.MouseInteraction;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;

public class Selection<T extends Node> implements NodeSubTreeSearch<T>, MouseInteraction {

    private final T node;

    public Selection(T node) {
	super();
	this.node = node;
    }

    @Override
    public T getNode() {
	return node;
    }

    public void click() {
	click(node);
    }

    public void click(MouseButton mouseButton) {
	click(node, mouseButton);
    }

    public boolean isPresent() {
	return node != null;
    }
}
