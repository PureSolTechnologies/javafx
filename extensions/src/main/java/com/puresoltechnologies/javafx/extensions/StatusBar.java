package com.puresoltechnologies.javafx.extensions;

import javafx.scene.Node;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * This is a special {@link ToolBar} for providing a defined way for status
 * bars.
 * 
 * @author Rick-Rainer Ludwig
 */
public class StatusBar extends HBox {

    public StatusBar() {
	super();
	initializeUI();
    }

    public StatusBar(Node... items) {
	super(items);
	initializeUI();
    }

    private void initializeUI() {
	InnerShadow is = new InnerShadow(0.0, Color.GRAY);
	is.setOffsetX(1.0f);
	is.setOffsetY(1.0f);
	setEffect(is);
    }

}
