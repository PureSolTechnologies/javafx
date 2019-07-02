package com.puresoltechnologies.javafx.testing.select;

import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Region;

public interface MenuSelector extends NodeSearch {

    default Selection<?> findMenuById(String id) {
	Selection<MenuButton> button = findNodeById(MenuButton.class, id);
	if (button != null) {
	    return button;
	}
	Selection<Region> container = findNodeById(Region.class, id);
	if (container != null) {
	    return container;
	}
	return null;
    }

    default Selection<?> findMenuByText(String text) {
	Selection<MenuButton> button = findNodeByText(MenuButton.class, text);
	if (button != null) {
	    return button;
	}
	Selection<Label> label = findNodeByText(Label.class, text);
	if (label != null) {
	    return label;
	}
	return null;
    }
}
