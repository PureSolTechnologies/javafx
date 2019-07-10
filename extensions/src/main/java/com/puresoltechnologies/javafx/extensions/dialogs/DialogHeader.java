package com.puresoltechnologies.javafx.extensions.dialogs;

import com.puresoltechnologies.javafx.utils.FXDefaultFonts;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

/**
 * This class provides a dialog header with an image, title and text. It looks
 * more modern than the original header text with graphics.
 *
 * @author Rick-Rainer Ludwig
 */
public class DialogHeader extends GridPane {

    private final ImageView imageView = new ImageView();
    private final Label titleLabel = new Label();
    private final Label descriptionTextArea = new Label();

    public DialogHeader() {
	setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

	GridPane.setConstraints(imageView, 0, 0, 1, 2, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.SOMETIMES,
		new Insets(5));
	GridPane.setConstraints(titleLabel, 1, 0, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.ALWAYS, Priority.SOMETIMES,
		new Insets(5));
	GridPane.setConstraints(descriptionTextArea, 1, 1, 1, 1, HPos.LEFT, VPos.TOP, Priority.ALWAYS,
		Priority.SOMETIMES, new Insets(5));

	titleLabel.setFont(FXDefaultFonts.titleFont);

	imageView.resize(64, 64);

	getChildren().addAll(imageView, titleLabel, descriptionTextArea);
    }

    public final StringProperty getTitleProperty() {
	return titleLabel.textProperty();
    }

    public final void setTitle(String title) {
	titleLabel.setText(title);
    }

    public final String getTitle() {
	return titleLabel.getText();
    }

    public final StringProperty descriptionProperty() {
	return descriptionTextArea.textProperty();
    }

    public final void setDescription(String description) {
	descriptionTextArea.setText(description);
    }

    public final String getDescription() {
	return descriptionTextArea.getText();
    }

    public final ObjectProperty<Image> imageProperty() {
	return imageView.imageProperty();
    }

    public final void setImage(Image image) {
	imageView.setImage(image);
    }

    public final Image getImage() {
	return imageView.getImage();
    }
}
