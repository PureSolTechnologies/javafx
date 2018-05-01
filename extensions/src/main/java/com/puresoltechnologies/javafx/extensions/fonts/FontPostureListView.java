package com.puresoltechnologies.javafx.extensions.fonts;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.FontPosture;

/**
 * This is a simple {@link ListView} for FontPosture selection.
 * 
 * @author Rick-Rainer Ludwig
 */
public class FontPostureListView extends ListView<FontPosture> {

    public FontPostureListView(FontPosture posture) {
	this();
	getSelectionModel().select(posture);
    }

    public FontPostureListView() {
	setItems(FXCollections.observableArrayList(FontPosture.values()));
	setCellFactory(view -> {
	    ListCell<FontPosture> listCell = new ListCell<FontPosture>() {
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
