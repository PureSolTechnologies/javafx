package com.puresoltechnologies.javafx.extensions;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * This component is a convenience implementation of a file chooser component
 * for dialogs and forms. The file chooser component contains of a
 * {@link Label}, a {@link TextField} and also a Choose {@link ButtonType}.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public abstract class AbstractChooserComponent<T> extends GridPane {

    private final StringProperty titleProperty = new SimpleStringProperty("");
    private final StringProperty buttonTextProperty = new SimpleStringProperty("Choose...");

    private final Label label = new Label();
    private final TextField textField = new TextField();
    private final Button button = new Button();

    public AbstractChooserComponent() {
	label.textProperty().bind(titleProperty);
	label.setAlignment(Pos.CENTER_RIGHT);
	button.textProperty().bind(buttonTextProperty);
	button.setOnAction(event -> {
	    String text = callChooserDialog();
	    textField.setText(text);
	    event.consume();
	});

	setConstraints(label, 0, 0, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.SOMETIMES, Priority.NEVER);
	setConstraints(textField, 1, 0, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
	setConstraints(button, 2, 0, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.NEVER, Priority.NEVER);

	setMargin(label, new Insets(5.0));

	getChildren().addAll(label, textField, button);
    }

    protected abstract String callChooserDialog();

    protected abstract T convertResult(String text);

    public AbstractChooserComponent(String title) {
	this();
	titleProperty.set(title);
    }

    public T getSelection() {
	return convertResult(textField.getText());
    }

    public String getTitle() {
	return titleProperty.get();
    }

    public void setTitle(String title) {
	titleProperty.set(title);
    }

    public StringProperty titleProperty() {
	return titleProperty;
    }

    public String getButtonText() {
	return buttonTextProperty.get();
    }

    public void setButtonText(String buttonText) {
	buttonTextProperty.set(buttonText);
    }

    public StringProperty buttonTextProperty() {
	return buttonTextProperty;
    }
}
