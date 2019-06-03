package com.puresoltechnologies.javafx.showroom.parts;

import java.io.IOException;
import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.parts.AbstractViewer;
import com.puresoltechnologies.javafx.perspectives.parts.PartContentType;
import com.puresoltechnologies.javafx.perspectives.parts.PartOpenMode;
import com.puresoltechnologies.javafx.showroom.ShowRoom;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class BoxPlotSampleViewer extends AbstractViewer {

    private final BorderPane borderPane = new BorderPane();

    public BoxPlotSampleViewer() {
	super("Box Plot Sample", PartOpenMode.AUTO_AND_MANUAL, PartContentType.ONE_PER_PERSPECTIVE);
	try {
	    setImage(ResourceUtils.getImage(ShowRoom.class, "icons/FatCow_Icons16x16/chart_bar.png"));
	} catch (IOException e) {
	    e.printStackTrace();
	}
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
	// TODO Auto-generated method stub

    }

    @Override
    public Node getContent() {
	return borderPane;
    }

}
