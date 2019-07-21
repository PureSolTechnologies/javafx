package com.puresoltechnologies.javafx.extensions.dialogs;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This dialog implementation is an alternative implementation to the original
 * Dialog from Java FX.
 *
 * @author Rick-Rainer Ludwig
 *
 * @param <T> is the return value type.
 */
public class Dialog<T> {

    private final Stage stage;
    private final Scene scene;
    private final BorderPane dialogPane = new BorderPane();
    private final DialogHeader dialogHeader = new DialogHeader();
    private final HBox buttonBar = new HBox();
    private final ObjectProperty<T> valueProperty = new SimpleObjectProperty<>(null);
    private boolean accepted = false;

    public Dialog() {
	stage = new Stage(StageStyle.DECORATED);
	scene = new Scene(dialogPane);
	stage.setScene(scene);

	dialogPane.setTop(dialogHeader);
	dialogPane.setBottom(buttonBar);

	Button okButton = new Button("OK");
	okButton.setOnAction((event) -> {
	    Dialog.this.stage.close();
	    event.consume();
	});
	addButton(okButton);
    }

    public StringProperty titleProperty() {
	return stage.titleProperty();
    }

    public String getTitle() {
	return stage.titleProperty().getValue();
    }

    public void setTitle(String title) {
	stage.titleProperty().setValue(title);
    }

    public StringProperty headerTitleProperty() {
	return dialogHeader.titleProperty();
    }

    public String getHeaderTitle() {
	return dialogHeader.titleProperty().getValue();
    }

    public void setHeaderTitle(String title) {
	dialogHeader.titleProperty().setValue(title);
    }

    public StringProperty descriptionProperty() {
	return dialogHeader.descriptionProperty();
    }

    public String getDescription() {
	return dialogHeader.descriptionProperty().getValue();
    }

    public void setDescription(String description) {
	dialogHeader.descriptionProperty().setValue(description);
    }

    public ObjectProperty<Image> imageProperty() {
	return dialogHeader.imageProperty();
    }

    public Image getImage() {
	return dialogHeader.imageProperty().getValue();
    }

    public void setImage(Image image) {
	dialogHeader.imageProperty().setValue(image);
    }

    public ObjectProperty<T> valuePropery() {
	return valueProperty;
    }

    public T getValue() {
	return valueProperty.getValue();
    }

    protected void setReturnValue(T returnValue) {
	this.valueProperty.setValue(returnValue);
    }

    public final Scene getScene() {
	return scene;
    }

    public void show() {
	stage.setAlwaysOnTop(false);
	stage.initModality(Modality.NONE);
	stage.show();
	stage.setOnCloseRequest((event) -> cancel());
    }

    protected void cancel() {
	accepted = false;
    }

    protected void accept() {
	accepted = true;
    }

    public boolean isAccepted() {
	return accepted;
    }

    public void showAndWait() {
	stage.setAlwaysOnTop(true);
	stage.initModality(Modality.APPLICATION_MODAL);
	stage.showAndWait();
    }

    public void addButton(Button button) {
	buttonBar.getChildren().add(button);
    }
}
