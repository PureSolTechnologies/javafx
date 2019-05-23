package com.puresoltechnologies.javafx.showroom.perspectives;

import java.io.IOException;
import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.AbstractPerspective;
import com.puresoltechnologies.javafx.perspectives.PartSplit;
import com.puresoltechnologies.javafx.perspectives.PartStack;
import com.puresoltechnologies.javafx.perspectives.PerspectiveElement;
import com.puresoltechnologies.javafx.showroom.ShowRoom;
import com.puresoltechnologies.javafx.showroom.parts.StepInterfaceExamplePart;
import com.puresoltechnologies.javafx.showroom.parts.WizardExamplePart;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.geometry.Orientation;
import javafx.scene.image.Image;

public class StartPerspective extends AbstractPerspective {

    public StartPerspective() {
	super("Show Room Start");
    }

    @Override
    public Optional<Image> getImage() {
	try {
	    return Optional.of(ResourceUtils.getImage(ShowRoom.class, "icons/FatCow_Icons32x32/application_home.png"));
	} catch (IOException e) {
	    return Optional.empty();
	}
    }

    @Override
    protected PerspectiveElement createContent() {
	PartStack leftStack = new PartStack();
	leftStack.addElement(StepInterfaceExamplePart.class);
	PartStack rightStack = new PartStack();
	rightStack.addElement(WizardExamplePart.class);

	PartSplit partSplit = new PartSplit(Orientation.HORIZONTAL);
	partSplit.addElement(0, leftStack);
	partSplit.addElement(1, rightStack);
	return partSplit;
    }

}
