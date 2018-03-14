package com.puresoltechnologies.javafx.extensions.fonts;

import java.util.Optional;

import com.puresoltechnologies.javafx.extensions.dialogs.FontSelectionDialog;

import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

@DefaultProperty("value")
public class FontSelector extends BorderPane {

    private static final String DEFAULT_STYLE_CLASS = "font-picker";

    private ObjectProperty<FontDefinition> value = new SimpleObjectProperty<FontDefinition>(this, "value");
    private final Label label = new Label("Nothing selected...");
    private final Button button = new Button("...");

    public FontSelector(FontDefinition font) {
	this();
	setValue(font);
    }

    public FontSelector() {
	getStyleClass().add(DEFAULT_STYLE_CLASS);

	setCenter(label);
	setRight(button);

	valueProperty().addListener(event -> {
	    label.setText(value.get().toString());
	});
	button.setOnAction(event -> selectFont());
    }

    private void selectFont() {
	FontSelectionDialog dialog = new FontSelectionDialog();
	if (getValue() != null) {
	    dialog.setFont(getValue());
	}
	Optional<FontDefinition> result = dialog.showAndWait();
	if (result.isPresent()) {
	    setValue(result.get());
	}
    }

    public final void setValue(FontDefinition font) {
	value.set(font);
    }

    public final FontDefinition getValue() {
	return value.get();
    }

    public final ObjectProperty<FontDefinition> valueProperty() {
	return value;
    }
}
