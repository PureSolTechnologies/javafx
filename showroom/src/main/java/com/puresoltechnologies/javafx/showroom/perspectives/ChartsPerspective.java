package com.puresoltechnologies.javafx.showroom.perspectives;

import java.io.IOException;
import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.AbstractPerspective;
import com.puresoltechnologies.javafx.perspectives.PartStack;
import com.puresoltechnologies.javafx.perspectives.PerspectiveElement;
import com.puresoltechnologies.javafx.showroom.ShowRoom;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.image.Image;

public class ChartsPerspective extends AbstractPerspective {

    public ChartsPerspective() {
	super("Charts Perspective");
    }

    @Override
    public Optional<Image> getImage() {
	try {
	    return Optional.of(ResourceUtils.getImage(ShowRoom.class, "icons/FatCow_Icons32x32/chart_line.png"));
	} catch (IOException e) {
	    return Optional.empty();
	}
    }

    @Override
    protected PerspectiveElement createContent() {
	PartStack partStack = new PartStack();
	return partStack;
    }

}
