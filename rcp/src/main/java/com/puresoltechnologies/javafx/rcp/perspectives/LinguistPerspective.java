package com.puresoltechnologies.javafx.rcp.perspectives;

import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.AbstractPerspective;
import com.puresoltechnologies.javafx.perspectives.PartStack;
import com.puresoltechnologies.javafx.perspectives.PerspectiveElement;
import com.puresoltechnologies.javafx.rcp.parts.LinguistPart;

import javafx.scene.image.Image;

public class LinguistPerspective extends AbstractPerspective {

    public LinguistPerspective() {
	super("Linguist");
    }

    @Override
    public Optional<Image> getImage() {
	return Optional.empty();
    }

    @Override
    protected PerspectiveElement createContent() {
	PartStack partStack = new PartStack();
	partStack.addElement(LinguistPart.class);
	return partStack;
    }
}
