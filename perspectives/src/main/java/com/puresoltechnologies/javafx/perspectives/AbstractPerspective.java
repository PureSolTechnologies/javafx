package com.puresoltechnologies.javafx.perspectives;

import javafx.scene.Node;

public abstract class AbstractPerspective implements Perspective {

    private final PerspectiveHandler perspectiveHandler;
    private final String name;
    private PerspectiveElement element = null;

    public AbstractPerspective(String name) {
	super();
	this.perspectiveHandler = new PerspectiveHandler(this);
	this.name = name;
	createNewContent();
    }

    private void createNewContent() {
	element = createContent();
	((AbstractPerspectiveElement) element).setPerspectiveHandler(perspectiveHandler);
    }

    protected abstract PerspectiveElement createContent();

    @Override
    public final String getName() {
	return name;
    }

    @Override
    public final void reset() {
	createNewContent();
    }

    @Override
    public final PerspectiveElement getElement() {
	return element;
    }

    @Override
    public final Node getContent() {
	return element.getContent();
    }

}
