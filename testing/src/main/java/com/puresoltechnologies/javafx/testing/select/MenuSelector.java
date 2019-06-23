package com.puresoltechnologies.javafx.testing.select;

import com.sun.javafx.scene.control.ContextMenuContent.MenuItemContainer;
import com.sun.javafx.scene.control.MenuBarButton;

import javafx.scene.control.Label;

public interface MenuSelector extends NodeFullSearch {

    default Selection<?> getMenuById(String id) {
	Selection<MenuBarButton> button = findNodeById(MenuBarButton.class, id);
	if (button != null) {
	    return button;
	}
	Selection<MenuItemContainer> container = findNodeById(MenuItemContainer.class, id);
	if (container != null) {
	    return container;
	}
	return null;
    }

    default Selection<?> getMenuByText(String text) {
	Selection<MenuBarButton> button = findNodeByText(MenuBarButton.class, text);
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
