package com.puresoltechnologies.javafx.extensions.toolbar;

import javafx.scene.input.DataFormat;

public class ToolBarDragDataFormat extends DataFormat {

    private static final ToolBarDragDataFormat instance = new ToolBarDragDataFormat();

    public static ToolBarDragDataFormat get() {
	return instance;
    }

    private ToolBarDragDataFormat() {
	super("ToolBarDragData");
    }

}
