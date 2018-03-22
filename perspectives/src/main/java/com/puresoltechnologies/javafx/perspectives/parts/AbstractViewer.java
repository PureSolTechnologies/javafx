package com.puresoltechnologies.javafx.perspectives.parts;

/**
 * This is the abstract implementation of a Viewer. Viewers cannot have mutable
 * data.
 * 
 * @author Rick-Rainer Ludwig
 */
public abstract class AbstractViewer extends AbstractPart implements ViewerPart {

    private static final long serialVersionUID = -263198895540028972L;

    public AbstractViewer(String title, PartOpenMode openMode) {
	super(title, openMode);
    }

}
