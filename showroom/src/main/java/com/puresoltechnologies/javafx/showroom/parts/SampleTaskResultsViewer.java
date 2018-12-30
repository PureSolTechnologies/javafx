package com.puresoltechnologies.javafx.showroom.parts;

import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.parts.AbstractViewer;
import com.puresoltechnologies.javafx.perspectives.parts.PartOpenMode;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class SampleTaskResultsViewer extends AbstractViewer {

    public SampleTaskResultsViewer() {
	super("Sample Task Results", PartOpenMode.AUTO_AND_MANUAL);
    }

    @Override
    public Optional<PartHeaderToolBar> getToolBar() {
	return Optional.empty();
    }

    @Override
    public void initialize() {
	// TODO Auto-generated method stub

    }

    @Override
    public void close() {
	// intentionally left blank
    }

    @Override
    public Node getContent() {
	GridPane layout = new GridPane();
	return layout;
    }

}
