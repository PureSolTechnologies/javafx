package com.puresoltechnologies.javafx.extensions;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

public class ContentDisplayComboBox extends ComboBox<ContentDisplay> {

    public ContentDisplayComboBox(ContentDisplay contentDisplay) {
	this();
	getSelectionModel().select(contentDisplay);
    }

    public ContentDisplayComboBox() {
	setItems(FXCollections.observableArrayList(ContentDisplay.values()));
	setCellFactory(view -> {
	    ListCell<ContentDisplay> listCell = new ListCell<ContentDisplay>() {
		@Override
		protected void updateItem(ContentDisplay item, boolean empty) {
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
