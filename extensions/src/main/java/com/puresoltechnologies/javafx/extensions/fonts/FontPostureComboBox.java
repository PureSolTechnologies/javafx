package com.puresoltechnologies.javafx.extensions.fonts;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.text.FontPosture;

/**
 * This is a simple {@link ComboBox} for FontPosture selection.
 * 
 * @author Rick-Rainer Ludwig
 */
public class FontPostureComboBox extends ComboBox<FontPosture> {

    public FontPostureComboBox(FontPosture posture) {
	this();
	getSelectionModel().select(posture);
    }

    public FontPostureComboBox() {
	setItems(FXCollections.observableArrayList(FontPosture.values()));
	setCellFactory(view -> {
	    ListCell<FontPosture> listCell = new ListCell<>() {
		@Override
		protected void updateItem(FontPosture item, boolean empty) {
		    super.updateItem(item, empty);
		    setText(null);
		    if (item == null || empty) {
			setGraphic(null);
		    } else {
			Label label = new Label(item.name());
			setGraphic(label);
		    }
		}
	    };
	    return listCell;
	});
    }

}
