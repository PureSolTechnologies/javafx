package com.puresoltechnologies.javafx.perspectives.parts;

import java.io.IOException;
import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.PartStack;
import com.puresoltechnologies.javafx.perspectives.Perspective;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class PartHeader extends HBox {

    private boolean active = false;
    private final PartStack partStack;
    private final Part part;
    private final ContextMenu contextMenu = new ContextMenu();

    public PartHeader(PartStack partStack, Part part) {
	super();
	this.partStack = partStack;
	this.part = part;
	try {
	    setManaged(true);
	    setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID,
		    new CornerRadii(5.0, 5.0, 0.0, 0.0, false),
		    new BorderWidths(1.0, 1.0, 0.0, 1.0, false, false, false, false), new Insets(3, 3, 3, 3))));
	    Optional<Image> image = part.getImage();
	    if (image.isPresent()) {
		ImageView imageView = new ImageView(image.get());
		getChildren().add(imageView);
		setMargin(imageView, new Insets(2.0));
	    }
	    Label label = new Label(part.getTitle());
	    getChildren().add(label);
	    setMargin(label, new Insets(2.0));

	    Image crossImage = ResourceUtils.getImage(Perspective.class, "icons/FatCow_Icons16x16/cross.png");
	    ImageView crossView = new ImageView(crossImage);
	    getChildren().add(crossView);
	    setMargin(crossView, new Insets(2.0));

	    Image detachImage = ResourceUtils.getImage(Perspective.class,
		    "icons/FatCow_Icons16x16/extract_foreground_objects.png");

	    MenuItem closeItem = new MenuItem("Close", new ImageView(crossImage));
	    closeItem.setOnAction(event -> close());
	    MenuItem closeOthersItem = new MenuItem("Close Others");
	    closeOthersItem.setOnAction(event -> closeOthers());
	    SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
	    MenuItem closeAllItem = new MenuItem("Close All");
	    closeAllItem.setOnAction(event -> closeAll());
	    SeparatorMenuItem separatorMenuItem2 = new SeparatorMenuItem();
	    MenuItem detachItem = new MenuItem("Detach", new ImageView(detachImage));
	    detachItem.setOnAction(event -> detach());
	    // Add MenuItem to ContextMenu
	    contextMenu.getItems().addAll(closeItem, closeOthersItem, separatorMenuItem, closeAllItem,
		    separatorMenuItem2, detachItem);

	    setOnDragDetected(event -> {
		/* drag was detected, start a drag-and-drop gesture */
		Dragboard db = startDragAndDrop(TransferMode.MOVE);

		/* Put a string on a dragboard */
		ClipboardContent content = new ClipboardContent();
		content.put(PartDragDataFormat.get(), new PartDragData(partStack.getId(), part.getId()));
		db.setContent(content);

		event.consume();
	    });
	    setOnMouseClicked(event -> {
		if (event.getButton() == MouseButton.PRIMARY) {
		    partStack.setActive(part.getId());
		    event.consume();
		}
	    });
	    crossView.setOnMouseClicked(event -> {
		if (event.getButton() == MouseButton.PRIMARY) {
		    partStack.closePart(part);
		}
	    });
	    setOnContextMenuRequested(event -> {
		contextMenu.show(this, event.getScreenX(), event.getScreenY());
	    });
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private void close() {
	partStack.closePart(part);
    }

    private void closeOthers() {
	partStack.closeOtherParts(part);
    }

    private void closeAll() {
	partStack.closeAll();
    }

    private void detach() {
	partStack.detach(part);
    }

    public void setActive(boolean active) {
	if (active) {
	    setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
	} else {
	    setBackground(null);
	}
	this.active = active;
    }

    public boolean isActive() {
	return active;
    }
}
