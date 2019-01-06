package com.puresoltechnologies.javafx.perspectives;

import java.util.UUID;

public abstract class AbstractPerspectiveElement implements PerspectiveElement {

    private PerspectiveElement parent = null;
    private PerspectiveHandler perspectiveHandler = null;
    private final UUID id = UUID.randomUUID();

    public AbstractPerspectiveElement() {
	super();
    }

    public AbstractPerspectiveElement(PerspectiveElement parent) {
	super();
	this.parent = parent;
    }

    @Override
    public final UUID getId() {
	return id;
    }

    @Override
    public final PerspectiveElement getParent() {
	return parent;
    }

    public final void setParent(PerspectiveElement parent) {
	this.parent = parent;
    }

    protected final PerspectiveHandler getPerspectiveHandler() {
	return perspectiveHandler;
    }

    /**
     * This method is not final, as children might need to overwrite it to propagate
     * this setting to its children in a special way.
     *
     * @param perspectiveHandler is the {@link PerspectiveHandler} to be set.
     */
    protected void setPerspectiveHandler(PerspectiveHandler perspectiveHandler) {
	this.perspectiveHandler = perspectiveHandler;
    }

    protected final void setContext(PerspectiveElement element) {
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
