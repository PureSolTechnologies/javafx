package com.puresoltechnologies.javafx.perspectives;

import javafx.scene.layout.BorderPane;

public class PerspectiveContainerPane extends BorderPane {

    private PerspectiveElement rootElement = null;

    PerspectiveContainerPane() {
	super();
    }

    PerspectiveContainerPane(PerspectiveElement rootElement) {
	super();
	this.rootElement = rootElement;
	setCenter(rootElement.getContent());
    }

    void setRootElement(PerspectiveElement perspectiveElement) {
	this.rootElement = perspectiveElement;
	if (rootElement != null) {
	    setCenter(rootElement.getContent());
	} else {
	    setCenter(null);
	}
    }

}
