package com.puresoltechnologies.javafx.preferences.dialogs;

import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;

public class PreferencesDialog extends Dialog<Void> {

    public PreferencesDialog() {
	setTitle("Preferences");
	setHeaderText("Preferences of Trader.");
	setResizable(true);

	ListView<String> listView = new ListView<>();
	listView.setEditable(false);

	getDialogPane().setContent(listView);

	ButtonType buttonTypeOk = new ButtonType("OK", ButtonData.OK_DONE);
	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	ObservableList<ButtonType> buttonTypes = getDialogPane().getButtonTypes();
	buttonTypes.addAll(buttonTypeOk, buttonTypeCancel);
    }

}
