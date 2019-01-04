package com.puresoltechnologies.javafx.perspectives.parts;

/**
 * This is the abstract implementation of an Editor. Editors can have mutable
 * data.
 *
 * @author Rick-Rainer Ludwig
 */
public abstract class AbstractEditor extends AbstractPart implements EditorPart {

    public AbstractEditor(String title, PartOpenMode openMode) {
	super(title, openMode);
    }

}
