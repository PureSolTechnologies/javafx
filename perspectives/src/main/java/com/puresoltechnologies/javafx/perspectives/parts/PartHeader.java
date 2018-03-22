package com.puresoltechnologies.javafx.perspectives.parts;

import java.io.IOException;
import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.PartStack;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

    private boolean active = false;

    public PartHeader(PartStack partStack, Part part) {
	super();
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

	    ImageView crossView = ResourceUtils.getImageView(this, "/icons/FatCow_Icons16x16/cross.png");
	    getChildren().add(crossView);
	    setMargin(crossView, new Insets(2.0));

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
	    crossView.setOnMouseClicked(event -> {
		partStack.closePart(part);
	    });
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
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
