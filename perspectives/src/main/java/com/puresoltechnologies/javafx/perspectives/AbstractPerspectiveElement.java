package com.puresoltechnologies.javafx.perspectives;

public abstract class AbstractPerspectiveElement implements PerspectiveElement {

    private PerspectiveHandler perspectiveHandler = null;

    protected final PerspectiveHandler getPerspectiveHandler() {
	return perspectiveHandler;
    }

    protected void setPerspectiveHandler(PerspectiveHandler perspectiveHandler) {
	this.perspectiveHandler = perspectiveHandler;
    }

}
