package com.puresoltechnologies.javafx.showroom.perspectives;

import java.io.IOException;
import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.AbstractPerspective;
import com.puresoltechnologies.javafx.perspectives.PartSplit;
import com.puresoltechnologies.javafx.perspectives.PartStack;
import com.puresoltechnologies.javafx.perspectives.PerspectiveElement;
import com.puresoltechnologies.javafx.showroom.ShowRoom;
import com.puresoltechnologies.javafx.showroom.parts.BoxPlotSampleViewer;
import com.puresoltechnologies.javafx.showroom.parts.OhlcPlotSampleViewer;
import com.puresoltechnologies.javafx.showroom.parts.TimeseriesPlotSampleViewer;
import com.puresoltechnologies.javafx.showroom.parts.XYPlotSampleViewer;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.geometry.Orientation;
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
	PartSplit horizontal = new PartSplit();
	PartSplit leftVertical = new PartSplit(Orientation.VERTICAL);
	PartSplit rightVertical = new PartSplit(Orientation.VERTICAL);
	horizontal.addElement(leftVertical);
	horizontal.addElement(rightVertical);
	PartStack upperLeftStack = new PartStack();
	PartStack lowerLeftStack = new PartStack();
	PartStack upperRightStack = new PartStack();
	PartStack lowerRightStack = new PartStack();
	leftVertical.addElement(upperLeftStack);
	leftVertical.addElement(lowerLeftStack);
	rightVertical.addElement(upperRightStack);
	rightVertical.addElement(lowerRightStack);

	upperLeftStack.addElement(BoxPlotSampleViewer.class);
	upperRightStack.addElement(XYPlotSampleViewer.class);
	lowerLeftStack.addElement(OhlcPlotSampleViewer.class);
	lowerRightStack.addElement(TimeseriesPlotSampleViewer.class);

	return horizontal;
    }

}
