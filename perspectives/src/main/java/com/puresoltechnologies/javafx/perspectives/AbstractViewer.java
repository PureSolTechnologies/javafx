package com.puresoltechnologies.javafx.perspectives;

/**
 * This is the abstract implementation of a Viewer. Viewers cannot have mutable
 * data.
 * 
 * @author Rick-Rainer Ludwig
 */
public abstract class AbstractViewer extends AbstractPart implements ViewerPart {

    private static final long serialVersionUID = -263198895540028972L;

    public AbstractViewer(String name, boolean singleton) {
	super(name, singleton);
    }

}
