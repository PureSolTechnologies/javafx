package com.puresoltechnologies.javafx.showroom.perspectives;

import java.io.IOException;

import com.puresoltechnologies.javafx.perspectives.AbstractPerspective;
import com.puresoltechnologies.javafx.perspectives.PartSplit;
import com.puresoltechnologies.javafx.perspectives.PartStack;
import com.puresoltechnologies.javafx.perspectives.PerspectiveElement;
import com.puresoltechnologies.javafx.showroom.ShowRoom;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.geometry.Orientation;
import javafx.scene.image.Image;

public class StartPerspective extends AbstractPerspective {

    public StartPerspective() {
	super("Show Room Start");
    }

    @Override
    public Image getImage() {
	try {
	    return ResourceUtils.getImage(ShowRoom.class, "icons/FatCow_Icons32x32/application_home.png");
	} catch (IOException e) {
	    return null;
	}
    }

    @Override
    protected PerspectiveElement createContent() {
	PartSplit partSplit = new PartSplit(Orientation.HORIZONTAL);
	PartStack leftStack = new PartStack();
	PartStack rightStack = new PartStack();
	partSplit.addElement(0, leftStack);
	partSplit.addElement(1, rightStack);
	return partSplit;
    }

}
