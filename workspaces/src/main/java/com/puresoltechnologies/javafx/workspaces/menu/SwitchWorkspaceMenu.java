package com.puresoltechnologies.javafx.workspaces.menu;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.puresoltechnologies.javafx.utils.ResourceUtils;
import com.puresoltechnologies.javafx.workspaces.Workspace;
import com.puresoltechnologies.javafx.workspaces.WorkspaceSettings;

import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SwitchWorkspaceMenu extends Menu {

    private static final Image icon;
    static {
	try {
	    icon = ResourceUtils.getImage(Workspace.class, "icons/FatCow_Icons16x16/arrow_switch.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }
    private final Stage stage;

    public SwitchWorkspaceMenu(Stage stage) {
	super("Switch _Workspace", new ImageView(icon));
	this.stage = stage;
	initialize();
    }

    public SwitchWorkspaceMenu(Stage stage, String text) {
	super(text);
	this.stage = stage;
	initialize();
    }

    public SwitchWorkspaceMenu(Stage stage, String text, Node graphic) {
	super(text, graphic);
	this.stage = stage;
	initialize();
    }

    public SwitchWorkspaceMenu(Stage stage, String text, Node graphic, MenuItem... items) {
	super(text, graphic, items);
	this.stage = stage;
	initialize();
    }

    private void initialize() {
	WorkspaceSettings workspaceSettings = new WorkspaceSettings();
	List<File> directories = workspaceSettings.getFormerDirectories();
	directories.forEach(directory -> {
	    MenuItem menuItem = new MenuItem(directory.getAbsolutePath());
	    menuItem.setOnAction(event -> {
		workspaceSettings.readSettings(); // Refresh, just in case...
		workspaceSettings.setDirectory(directory);
		workspaceSettings.setRestarting(true);
		workspaceSettings.writeSettings();
		stage.close();
		event.consume();
	    });
	    getItems().add(menuItem);
	});
	getItems().add(new SeparatorMenuItem());
	getItems().add(new SwitchWorkspaceMenuItem(stage, "Other..."));
    }

}
