package com.puresoltechnologies.javafx.rcp.perspectives;

import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.AbstractPerspective;
import com.puresoltechnologies.javafx.perspectives.PartSplit;
import com.puresoltechnologies.javafx.perspectives.PerspectiveElement;

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
	return new PartSplit();
    }
}
