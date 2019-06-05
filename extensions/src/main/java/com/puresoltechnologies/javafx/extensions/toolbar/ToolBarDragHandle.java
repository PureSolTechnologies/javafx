package com.puresoltechnologies.javafx.extensions.toolbar;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.puresoltechnologies.javafx.extensions.StatusBar;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class ToolBarDragHandle extends Label {

    private static final Logger logger = LoggerFactory.getLogger(ToolBarDragHandle.class);

    private final DraggableToolBar draggableToolBar;

    public ToolBarDragHandle(DraggableToolBar draggableToolBar) {
	this.draggableToolBar = draggableToolBar;
	try {
	    ImageView dragHandle = ResourceUtils.getImageView(StatusBar.class, "icons/ToolBarDragHandle_16.png");
	    setGraphic(dragHandle);
	} catch (IOException e) {
	    logger.warn("Could not load drag handle image.", e);
	}
	setOnMouseEntered(event -> {
	    setCursor(Cursor.MOVE);
	    event.consume();
	});
	setOnMouseExited(event -> {
	    setCursor(Cursor.DEFAULT);
	    event.consume();
	});
	setOnDragDetected(dragEvent -> {
	    startDrag();
	    dragEvent.consume();
	});
    }

    private void startDrag() {
	/* drag was detected, start a drag-and-drop gesture */
	Dragboard db = startDragAndDrop(TransferMode.MOVE);

	/* Put a string on a dragboard */
	ClipboardContent content = new ClipboardContent();
	content.put(ToolBarDragDataFormat.get(), new ToolBarDragData(draggableToolBar.getId()));
	db.setContent(content);
    }

}
