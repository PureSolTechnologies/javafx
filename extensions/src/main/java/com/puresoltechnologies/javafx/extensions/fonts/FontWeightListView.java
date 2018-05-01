package com.puresoltechnologies.javafx.extensions.fonts;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.FontWeight;

/**
 * This is a simple {@link ListView} for {@link FontWeight} selection.
 * 
 * @author Rick-Rainer Ludwig
 */
public class FontWeightListView extends ListView<FontWeight> {

    public FontWeightListView(FontWeight fontWeight) {
	this();
	getSelectionModel().select(fontWeight);
    }

    public FontWeightListView() {
	setItems(FXCollections.observableArrayList(FontWeight.values()));
	setCellFactory(view -> {
	    ListCell<FontWeight> listCell = new ListCell<FontWeight>() {
		@Override
		protected void updateItem(FontWeight item, boolean empty) {
		    super.updateItem(item, empty);
		    setText(null);
		    if (item == null || empty) {
			setGraphic(null);
		    } else {
			Label label = new Label(item.name() + " (" + item.getWeight() + ")");
			setGraphic(label);
		    }
		}
	    };
	    return listCell;
	});
    }

}
