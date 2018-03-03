package com.puresoltechnologies.javafx.perspectives.parts;

import javafx.scene.input.DataFormat;

public class PartDragDataFormat extends DataFormat {

    private static final PartDragDataFormat instance = new PartDragDataFormat();

    public static PartDragDataFormat get() {
	return instance;
    }

    private PartDragDataFormat() {
	super("PartDragData");
    }

}
