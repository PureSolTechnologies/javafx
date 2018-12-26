package com.puresoltechnologies.javafx.perspectives;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.puresoltechnologies.javafx.perspectives.dialogs.PerspectiveSelectionDialog;
import com.puresoltechnologies.javafx.perspectives.dialogs.PartSelectionDialog;
import com.puresoltechnologies.javafx.perspectives.parts.Part;
import com.puresoltechnologies.javafx.preferences.Preferences;
import com.puresoltechnologies.javafx.utils.FXThreads;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class PerspectiveContainer extends BorderPane {

    private static final ObjectProperty<ContentDisplay> toolBarContentDisplay = Preferences
	    .getProperty(PerspectiveProperties.perspectiveToolbarContentDisplay);

    private final ToolBar toolBar;
    private final SplitMenuButton openPerspectiveButton;
    private final List<Perspective> perspectives = new ArrayList<>();
    private Perspective currentPerspective = null;

    PerspectiveContainer() {
	super();
	try {
	    ImageView switchWidowsImage = ResourceUtils.getImageView(this,
		    "icons/FatCow_Icons16x16/switch_windows.png");
	    openPerspectiveButton = new SplitMenuButton();
	    openPerspectiveButton.setText("Open...");
	    openPerspectiveButton.setGraphic(switchWidowsImage);
	    openPerspectiveButton.setId("OpenPerspectivesButton");
	    openPerspectiveButton.setContentDisplay(toolBarContentDisplay.get());
	    updateOpenPerspectiveButton();

	    ImageView watchWidowImage = ResourceUtils.getImageView(this, "icons/FatCow_Icons16x16/watch_window.png");
	    Button showViewButton = new Button("Show Part...", watchWidowImage);
	    showViewButton.setId("ShowViewButton");
	    showViewButton.setContentDisplay(toolBarContentDisplay.get());

	    ImageView resetPerspectiveImage = ResourceUtils.getImageView(this, "icons/FatCow_Icons16x16/undo.png");
	    Button resetButton = new Button("Reset", resetPerspectiveImage);
	    resetPerspectiveImage.setId("resetPerspectiveButton");
	    resetButton.setContentDisplay(toolBarContentDisplay.get());

	    ImageView closePerspectiveImage = ResourceUtils.getImageView(this, "icons/FatCow_Icons16x16/cross.png");
	    Button closeButton = new Button("Close", closePerspectiveImage);
	    closePerspectiveImage.setId("ClosePerspectiveButton");
	    closeButton.setContentDisplay(toolBarContentDisplay.get());

	    openPerspectiveButton.setOnAction(event -> openNewPerspective());
	    showViewButton.setOnAction(event -> showView());
	    resetButton.setOnAction(event -> resetCurrentPerspective());
	    closeButton.setOnAction(event -> closeCurrentPerspective());

	    toolBar = new ToolBar();
	    toolBar.getItems().addAll(openPerspectiveButton, showViewButton, resetButton, closeButton);
	    setTop(toolBar);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private void updateOpenPerspectiveButton() {
	ObservableList<MenuItem> items = openPerspectiveButton.getItems();
	items.clear();
	perspectives.forEach(perspective -> {
	    MenuItem item = new MenuItem(perspective.getName());
	    item.setOnAction(event -> {
		PerspectiveContainer.this.selectPerspective(perspective.getId());
		event.consume();
	    });
	    openPerspectiveButton.getItems().add(item);
	});
    }

    private void openNewPerspective() {
	Optional<Perspective> perspective = new PerspectiveSelectionDialog().showAndWait();
	if (perspective.isPresent()) {
	    addPerspective(perspective.get());
	}
    }

    private void showView() {
	Optional<Part> part = new PartSelectionDialog().showAndWait();
	if (part.isPresent()) {
	    PerspectiveService.openPart(part.get());
	    part.get().manualInitialization();
	}
    }

    private void resetCurrentPerspective() {
	if (currentPerspective != null) {
	    currentPerspective.reset();
	    setCenter(currentPerspective.getContent());
	}
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
	FXThreads.proceedOnFXThread(() -> {
	    updateOpenPerspectiveButton();
	    setCenter(currentPerspective.getContent());
	});
    }

    public void removeAllPerspectives() {
	perspectives.forEach(perspective -> closeAllParts(perspective));
	perspectives.clear();
	currentPerspective = null;
	FXThreads.proceedOnFXThread(() -> {
	    setCenter(null);
	    updateOpenPerspectiveButton();
	});
    }

    public void removePerspective(Perspective perspective) {
	closeAllParts(perspective);
	this.perspectives.remove(perspective);
	FXThreads.proceedOnFXThread(() -> {
	    if (perspectives.size() > 0) {
		currentPerspective = perspectives.get(perspectives.size() - 1);
		setCenter(currentPerspective.getContent());
	    } else {
		currentPerspective = null;
		setCenter(null);
	    }
	    updateOpenPerspectiveButton();
	});
    }

    public void removePerspective(String perspectiveId) {
	Iterator<Perspective> iterator = perspectives.iterator();
	while (iterator.hasNext()) {
	    Perspective perspective = iterator.next();
	    if (perspective.getId().equals(perspectiveId)) {
		closeAllParts(perspective);
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

    private void closeAllParts(Perspective perspective) {
	perspective.getElements().forEach(element -> closeAllParts(element));
    }

    private Object closeAllParts(PerspectiveElement element) {
	if (element instanceof PartStack) {
	    PartStack partStack = (PartStack) element;
	    partStack.getParts().forEach(part -> part.close());
	} else if (element instanceof PartSplit) {
	    element.getElements().forEach(e -> closeAllParts(e));
	}
	return null;
    }

    public void selectPerspective(UUID perspectiveId) {
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
