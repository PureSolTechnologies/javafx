package com.puresoltechnologies.javafx.workspaces.menu;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.puresoltechnologies.javafx.utils.ResourceUtils;
import com.puresoltechnologies.javafx.workspaces.WorkspaceSettings;
import com.puresoltechnologies.javafx.workspaces.dialogs.WorkspaceSelectionDialog;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SwitchWorkspaceMenuItem extends MenuItem {

    private static final Image icon;
    static {
	try {
	    icon = ResourceUtils.getImage(SwitchWorkspaceMenuItem.class, "/icons/FatCow_Icons16x16/arrow_switch.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }
    private final Stage stage;

    public SwitchWorkspaceMenuItem(Stage stage) {
	super("Switch Workspace...", new ImageView(icon));
	this.stage = stage;
	initialize();
    }

    public SwitchWorkspaceMenuItem(Stage stage, String text, Node graphic) {
	super(text, graphic);
	this.stage = stage;
	initialize();
    }

    public SwitchWorkspaceMenuItem(Stage stage, String text) {
	super(text);
	this.stage = stage;
	initialize();
    }

    private void initialize() {
	setOnAction(event -> {
	    WorkspaceSelectionDialog dialog = new WorkspaceSelectionDialog();
	    Optional<File> result = dialog.showAndWait();
	    if (result.isPresent()) {
		WorkspaceSettings workspaceSettings = new WorkspaceSettings();
		workspaceSettings.setRestarting(true);
		workspaceSettings.writeSettings();
		stage.close();
		event.consume();
	    }
	});
    }
}
