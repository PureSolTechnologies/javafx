package com.puresoltechnologies.javafx.perspectives.parts;

import java.io.IOException;

import com.puresoltechnologies.javafx.perspectives.PartStack;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
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

    private final Part part;
    private boolean active = false;

    public PartHeader(PartStack partStack, Part part) {
	super();
	this.part = part;
	try {
	    setPadding(Insets.EMPTY);
	    setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(0.0),
		    new BorderWidths(1.0, 1.0, 1.0, 1.0, false, false, false, false), new Insets(5, 5, 5, 5))));

	    ImageView imageView = ResourceUtils.getImageView(this, "/icons/FatCow_Icons16x16/cross.png");
	    imageView.setScaleX(0.5);
	    imageView.setScaleY(0.5);
	    getChildren().add(new Label(part.getName()));
	    getChildren().add(imageView);

	    setOnDragDetected(event -> {
		/* drag was detected, start a drag-and-drop gesture */
		/* allow any transfer mode */
		Dragboard db = startDragAndDrop(TransferMode.MOVE);

		/* Put a string on a dragboard */
		ClipboardContent content = new ClipboardContent();
		content.put(PartDragDataFormat.get(), new PartDragData(partStack.getId(), part.getId()));
		db.setContent(content);

		event.consume();
	    });
	    setOnMouseClicked(event -> {
		partStack.setActive(part.getId());
		event.consume();
	    });
	    imageView.setOnMouseClicked(event -> {
		partStack.removeElement(part);
	    });
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public void setActive(boolean active) {
	if (active) {
	    setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
	} else {
	    setBackground(null);
	}
	this.active = active;
    }

    public boolean isActive() {
	return active;
    }
}
