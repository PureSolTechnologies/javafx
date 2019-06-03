package com.puresoltechnologies.javafx.showroom.parts;

import java.util.Optional;

import com.puresoltechnologies.javafx.extensions.stepinterface.StepInterface;
import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.parts.AbstractViewer;
import com.puresoltechnologies.javafx.perspectives.parts.PartContentType;
import com.puresoltechnologies.javafx.perspectives.parts.PartOpenMode;

import javafx.scene.Node;

public class StepInterfaceExamplePart extends AbstractViewer {

    private final StepInterface stepInterface = new StepInterface();

    public StepInterfaceExamplePart() {
	super("Step Interface Example", PartOpenMode.AUTO_AND_MANUAL, PartContentType.ONE_PER_PERSPECTIVE);
    }

    @Override
    public Optional<PartHeaderToolBar> getToolBar() {
	return Optional.empty();
    }

    @Override
    public void close() {
	// Intentionally left blank
    }

    @Override
    public void initialize() {
	// TODO Auto-generated method stub
    }

    @Override
    public Node getContent() {
	return stepInterface;
    }

}
