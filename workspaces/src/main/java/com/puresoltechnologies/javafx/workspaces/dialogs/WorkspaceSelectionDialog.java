package com.puresoltechnologies.javafx.workspaces.dialogs;

import java.io.File;
import java.io.IOException;

import com.puresoltechnologies.javafx.utils.ResourceUtils;
import com.puresoltechnologies.javafx.workspaces.WorkspaceSettings;
import com.puresoltechnologies.javafx.workspaces.menu.SwitchWorkspaceMenuItem;

import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class WorkspaceSelectionDialog extends Dialog<File> {

    private static final Image iconSmall;
    private static final Image iconBig;
    static {
	try {
	    iconSmall = ResourceUtils.getImage(SwitchWorkspaceMenuItem.class,
		    "/icons/FatCow_Icons16x16/arrow_switch.png");
	    iconBig = ResourceUtils.getImage(SwitchWorkspaceMenuItem.class,
		    "/icons/FatCow_Icons32x32/arrow_switch.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }
    private final WorkspaceSettings workspaceSettings;
    private final WorkspaceSelectionPane settingsPane;

    public WorkspaceSelectionDialog() {
	super();
	setTitle("Workspace Selection");
	setHeaderText(WorkspaceSelectionPane.CONTENT_STRING);
	setGraphic(new ImageView(iconBig));
	Stage stage = (Stage) getDialogPane().getScene().getWindow();
	stage.getIcons().addAll(iconSmall, iconBig);
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
	workspaceSettings.writeSettings();
    }

}
