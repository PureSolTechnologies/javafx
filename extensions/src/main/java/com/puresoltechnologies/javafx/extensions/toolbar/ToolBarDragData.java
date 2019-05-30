package com.puresoltechnologies.javafx.extensions.toolbar;

import java.io.Serializable;

public class ToolBarDragData implements Serializable {

    private static final long serialVersionUID = -6547910080643631819L;

    private final String draggableToolBarId;

    public ToolBarDragData(String draggableToolBarId) {
	super();
	this.draggableToolBarId = draggableToolBarId;
    }

    public String getDraggableToolBarId() {
	return draggableToolBarId;
    }

}
