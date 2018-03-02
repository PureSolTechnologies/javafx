package com.puresoltechnologies.javafx.perspectives;

public abstract class AbstractPerspectiveElement implements PerspectiveElement {

    private static final long serialVersionUID = 2392435422868206813L;

    private PerspectiveElement parent = null;
    private PerspectiveHandler perspectiveHandler = null;

    public AbstractPerspectiveElement() {
	super();
    }

    public AbstractPerspectiveElement(PerspectiveElement parent) {
	super();
	this.parent = parent;
    }

    @Override
    public PerspectiveElement getParent() {
	return parent;
    }

    public void setParent(PerspectiveElement parent) {
	this.parent = parent;
    }

    protected final PerspectiveHandler getPerspectiveHandler() {
	return perspectiveHandler;
    }

    protected void setPerspectiveHandler(PerspectiveHandler perspectiveHandler) {
	this.perspectiveHandler = perspectiveHandler;
    }

    protected void setContext(PerspectiveElement element) {
	if (element instanceof PartSplit) {
	    ((PartSplit) element).setParent(this);
	    ((PartSplit) element).setPerspectiveHandler(getPerspectiveHandler());
	} else if (element instanceof PartStack) {
	    ((PartStack) element).setParent(this);
	    ((PartStack) element).setPerspectiveHandler(getPerspectiveHandler());
	} else {
	    throw new IllegalStateException("Element of type '" + element.getClass().getName() + "' is not supported.");
	}
    }

}
