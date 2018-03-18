package com.puresoltechnologies.javafx.perspectives;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ToolBar;

/**
 * This is a specialized {@link ToolBar} used for part headers and part header
 * toolbars.
 * 
 * @author Rick-Rainer Ludwig
 */
public class PartHeaderToolBar extends ToolBar {

    public PartHeaderToolBar() {
	super();
	initialize();
    }

    public PartHeaderToolBar(Node... arg0) {
	super(arg0);
	initialize();
    }

    private void initialize() {
	setPadding(Insets.EMPTY);
	setManaged(true);
    }
}
