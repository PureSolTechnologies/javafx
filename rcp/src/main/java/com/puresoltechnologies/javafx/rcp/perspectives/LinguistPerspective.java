package com.puresoltechnologies.javafx.rcp.perspectives;

import java.io.IOException;
import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.AbstractPerspective;
import com.puresoltechnologies.javafx.perspectives.PartStack;
import com.puresoltechnologies.javafx.perspectives.PerspectiveElement;
import com.puresoltechnologies.javafx.rcp.Rcp;
import com.puresoltechnologies.javafx.rcp.parts.LinguistPart;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.image.Image;

public class LinguistPerspective extends AbstractPerspective {

    public LinguistPerspective() {
	super("Linguist");
    }

    @Override
    public Optional<Image> getImage() {
	try {
	    return Optional.of(ResourceUtils.getImage(Rcp.class, "icons/FatCow_Icons32x32/change_language.png"));
	} catch (IOException e) {
	    return Optional.empty();
	}
    }

    @Override
    protected PerspectiveElement createContent() {
	PartStack partStack = new PartStack();
	partStack.addElement(LinguistPart.class);
	return partStack;
    }
}
