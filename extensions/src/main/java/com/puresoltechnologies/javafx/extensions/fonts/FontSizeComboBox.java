package com.puresoltechnologies.javafx.extensions.fonts;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

/**
 * This is a simple {@link ComboBox} for FontWeight selection.
 * 
 * @author Rick-Rainer Ludwig
 */
public class FontSizeComboBox extends ComboBox<Double> {

    private static final Double[] defaultSizes = { 6.0, 8.0, 10.0, 11.0, 12.0, 14.0, 16.0, 18.0, 20.0, 24.0, 32.0,
	    48.0 };

    public FontSizeComboBox(double size) {
	this();
	getSelectionModel().select(size);
    }

    public FontSizeComboBox() {
	setItems(FXCollections.observableArrayList(defaultSizes));
	setEditable(true);
    }

}
