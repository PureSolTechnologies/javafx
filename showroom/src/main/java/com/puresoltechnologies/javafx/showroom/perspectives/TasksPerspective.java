package com.puresoltechnologies.javafx.showroom.perspectives;

import java.io.IOException;
import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.AbstractPerspective;
import com.puresoltechnologies.javafx.perspectives.PartSplit;
import com.puresoltechnologies.javafx.perspectives.PartStack;
import com.puresoltechnologies.javafx.perspectives.PerspectiveElement;
import com.puresoltechnologies.javafx.perspectives.parts.TaskProgressViewer;
import com.puresoltechnologies.javafx.showroom.ShowRoom;
import com.puresoltechnologies.javafx.showroom.parts.SampleTaskResultsViewer;
import com.puresoltechnologies.javafx.showroom.parts.StartSampleTasksViewer;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.geometry.Orientation;
import javafx.scene.image.Image;

public class TasksPerspective extends AbstractPerspective {

    public TasksPerspective() {
	super("Tasks Perspective");
    }

    @Override
    public Optional<Image> getImage() {
	try {
	    return Optional.of(ResourceUtils.getImage(ShowRoom.class, "icons/FatCow_Icons32x32/cog.png"));
	} catch (IOException e) {
	    return Optional.empty();
	}
    }

    @Override
    protected PerspectiveElement createContent() {
	PartSplit mainSplit = new PartSplit(Orientation.VERTICAL);
	PartSplit subSplit = new PartSplit(Orientation.HORIZONTAL);
	PartStack leftStack = new PartStack();
	PartStack rightStack = new PartStack();
	subSplit.addElement(leftStack);
	subSplit.addElement(rightStack);
	PartStack bottomStack = new PartStack();
	mainSplit.addElement(subSplit);
	mainSplit.addElement(bottomStack);

	leftStack.addElement(StartSampleTasksViewer.class);
	rightStack.addElement(SampleTaskResultsViewer.class);
	bottomStack.addElement(TaskProgressViewer.class);

	return mainSplit;
    }

}
