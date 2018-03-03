package com.puresoltechnologies.javafx.perspectives.parts;

/**
 * This is the abstract implementation of an Editor. Editors can have mutable
 * data.
 * 
 * @author Rick-Rainer Ludwig
 */
public abstract class AbstractEditor extends AbstractPart implements EditorPart {

    private static final long serialVersionUID = 3352125016364928485L;

    public AbstractEditor(String name, boolean singleton) {
	super(name, singleton);
    }

}
