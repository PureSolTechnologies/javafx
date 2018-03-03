package com.puresoltechnologies.javafx.perspectives;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.dialogs.PerspectiveSelectionDialog;
import com.puresoltechnologies.javafx.perspectives.parts.Part;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class PerspectiveContainer extends BorderPane {

    private final ToolBar toolBar;
    private final List<Perspective> perspectives = new ArrayList<>();
    private Perspective currentPerspective = null;

    PerspectiveContainer() {
	super();
	try {
	    ImageView switchWidowsImage = ResourceUtils.getImageView(this,
		    "/icons/FatCow_Icons16x16/switch_windows.png");
	    Button openPerspectiveButton = new Button("Open...", switchWidowsImage);
	    openPerspectiveButton.setId("OpenPerspectivesButton");
	    openPerspectiveButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	    ImageView resetPerspectiveImage = ResourceUtils.getImageView(this, "/icons/FatCow_Icons16x16/undo.png");
	    Button resetButton = new Button("Reset", resetPerspectiveImage);
	    openPerspectiveButton.setId("resetPerspectiveButton");
	    resetButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	    ImageView closePerspectiveImage = ResourceUtils.getImageView(this, "/icons/FatCow_Icons16x16/cross.png");
	    Button closeButton = new Button("Close", closePerspectiveImage);
	    openPerspectiveButton.setId("ClosePerspectiveButton");
	    closeButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

	    openPerspectiveButton.setOnAction(event -> openNewPerspective());
	    resetButton.setOnAction(event -> resetCurrentPerspective());
	    closeButton.setOnAction(event -> closeCurrentPerspective());

	    toolBar = new ToolBar();
	    toolBar.getItems().addAll(openPerspectiveButton, resetButton, closeButton);
	    setTop(toolBar);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private void openNewPerspective() {
	Optional<Perspective> perspective = new PerspectiveSelectionDialog().showAndWait();
	if (perspective.isPresent()) {
	    addPerspective(perspective.get());
	}
    }

    private void resetCurrentPerspective() {
	currentPerspective.reset();
	setCenter(currentPerspective.getContent());
    }

    private void closeCurrentPerspective() {
	removePerspective(currentPerspective);
    }

    public void addPerspective(Class<? extends Perspective> clazz) {
	try {
	    Perspective perspective = clazz.getConstructor().newInstance();
	    addPerspective(perspective);
	} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
		| NoSuchMethodException | SecurityException e) {
	    throw new RuntimeException();
	}
    }

    public void addPerspective(Perspective perspective) {
	this.perspectives.add(perspective);
	currentPerspective = perspective;
	Platform.runLater(() -> setCenter(currentPerspective.getContent()));
    }

    public void removePerspective(Perspective perspective) {
	this.perspectives.remove(perspective);
	if (perspectives.size() > 0) {
	    currentPerspective = perspectives.get(perspectives.size() - 1);
	    setCenter(currentPerspective.getContent());
	} else {
	    currentPerspective = null;
	    setCenter(null);
	}
    }

    public void removePerspective(String perspectiveId) {
	Iterator<Perspective> iterator = perspectives.iterator();
	while (iterator.hasNext()) {
	    Perspective perspective = iterator.next();
	    if (perspective.getId().equals(perspectiveId)) {
		iterator.remove();
	    }
	}
	if (perspectives.size() > 0) {
	    currentPerspective = perspectives.get(perspectives.size() - 1);
	    setCenter(currentPerspective.getContent());
	} else {
	    setCenter(null);
	}
    }

    public void selectPerspective(String perspectiveId) {
	Iterator<Perspective> iterator = perspectives.iterator();
	while (iterator.hasNext()) {
	    Perspective perspective = iterator.next();
	    if (perspective.getId().equals(perspectiveId)) {
		currentPerspective = perspective;
		setCenter(currentPerspective.getContent());
	    }
	}
    }

    public Perspective getCurrentPerspective() {
	return currentPerspective;
    }

    public void showView(Part part) {
	// TODO Auto-generated method stub
    }
}
