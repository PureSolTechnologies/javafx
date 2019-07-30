package com.puresoltechnologies.javafx.extensions;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * This is a special {@link ToolBar} for providing a defined way for status
 * bars. It is based on {@link HBox} and is filled and configured like it.
 *
 * @author Rick-Rainer Ludwig
 */
public class StatusBar extends HBox {

    /**
     * Default constructor.
     */
    public StatusBar() {
	super();
	initializeUI();
    }

    /**
     * Constructor which also takes the children to be shown in statusbar.s
     */
    public StatusBar(Node... items) {
	super(items);
	initializeUI();
    }

    private void initializeUI() {
	setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, Color.LIGHTGRAY, Color.LIGHTGRAY, Color.LIGHTGRAY,
		BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
		CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
    }

}
