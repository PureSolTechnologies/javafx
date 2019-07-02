package com.puresoltechnologies.javafx.testing.select;

import static org.junit.jupiter.api.Assertions.fail;

import com.puresoltechnologies.javafx.testing.ReplayTimings;
import com.puresoltechnologies.javafx.testing.mouse.MouseInteraction;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;

public class Selection<T extends Node> implements NodeSubTreeSearch<T>, MouseInteraction, ButtonSelector, MenuSelector {

    private final T node;

    public Selection(T node) {
	super();
	this.node = node;
    }

    @Override
    public T getNode() {
	try {
	    Thread.sleep(ReplayTimings.getNodeRetrievalDelay());
	} catch (InterruptedException e) {
	    fail("Wait interrupted.", e);
	}
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
