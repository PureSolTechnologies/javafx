package com.puresoltechnologies.javafx.extensions.fonts;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;

/**
 * This is a simple {@link ListView} for FontFamily selection.
 * 
 * @author Rick-Rainer Ludwig
 */
public class FontFamilyListView extends ListView<String> {

    public FontFamilyListView(String family) {
	this();
	getSelectionModel().select(family);
    }

    public FontFamilyListView() {
	setItems(FXCollections.observableArrayList(Font.getFamilies()));
	setCellFactory(view -> {
	    ListCell<String> listCell = new ListCell<String>() {
		@Override
		protected void updateItem(String item, boolean empty) {
		    super.updateItem(item, empty);
		    setText(null);
		    if (item == null || empty) {
			setGraphic(null);
		    } else {
			Label label = new Label(item);
			label.setFont(Font.font(item));
			setGraphic(label);
		    }
		}
	    };
	    return listCell;
	});
    }

}
