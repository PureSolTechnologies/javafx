package com.puresoltechnologies.javafx.extensions.toolbar;

import javafx.scene.Node;
import javafx.scene.control.ToolBar;

/**
 * This class provides an extended {@link ToolBar} which provides dragging.
 *
 * @author Rick-Rainer Ludwig
 */
public class DraggableToolBar extends ToolBar {

    private final ToolBarDragHandle dragHandle = new ToolBarDragHandle(this);

    public DraggableToolBar() {
	super();
	getItems().add(dragHandle);
    }

    public DraggableToolBar(Node... items) {
	super();
	getItems().add(dragHandle);
	getItems().addAll(items);
    }

}
