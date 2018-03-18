package com.puresoltechnologies.javafx.workspaces.dialogs;

import java.io.File;

import com.puresoltechnologies.javafx.workspaces.WorkspaceSettings;

import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class WorkspaceSelectionDialog extends Dialog<File> {

    private final WorkspaceSettings workspaceSettings;
    private final WorkspaceSelectionPane settingsPane;

    public WorkspaceSelectionDialog() {
	setTitle("Workspace Selection");
	setHeaderText(WorkspaceSelectionPane.CONTENT_STRING);
	setResizable(true);

	workspaceSettings = new WorkspaceSettings();
	settingsPane = new WorkspaceSelectionPane(workspaceSettings);
	getDialogPane().setContent(settingsPane);

	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	ButtonType buttonTypeOK = new ButtonType("Launch", ButtonData.OK_DONE);
	ObservableList<ButtonType> buttonTypes = getDialogPane().getButtonTypes();
	buttonTypes.addAll(buttonTypeCancel, buttonTypeOK);

	setResultConverter((buttonType) -> {
	    if (buttonType == buttonTypeOK) {
		saveSetting();
		return workspaceSettings.getDirectory();
	    } else {
		return null;
	    }
	});
    }

    private void saveSetting() {
	workspaceSettings.updateFormerDirectories();
	workspaceSettings.writeSettings();
    }

}
