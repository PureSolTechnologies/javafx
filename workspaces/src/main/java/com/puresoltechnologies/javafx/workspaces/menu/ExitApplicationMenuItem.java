package com.puresoltechnologies.javafx.workspaces.menu;

import java.io.IOException;

import com.puresoltechnologies.javafx.utils.ResourceUtils;
import com.puresoltechnologies.javafx.workspaces.WorkspaceSettings;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ExitApplicationMenuItem extends MenuItem {

    private static final Image icon;
    static {
	try {
	    icon = ResourceUtils.getImage(ExitApplicationMenuItem.class, "/icons/FatCow_Icons16x16/door_out.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }
    private final Stage stage;

    public ExitApplicationMenuItem(Stage stage) {
	super("Exit", new ImageView(icon));
	this.stage = stage;
	initialize();
    }

    public ExitApplicationMenuItem(Stage application, String text, Node graphic) {
	super(text, graphic);
	this.stage = application;
	initialize();
    }

    public ExitApplicationMenuItem(Stage application, String text) {
	super(text);
	this.stage = application;
	initialize();
    }

    private void initialize() {
	setOnAction(event -> {
	    WorkspaceSettings workspaceSettings = new WorkspaceSettings();
	    workspaceSettings.setRestarting(false);
	    workspaceSettings.writeSettings();
	    stage.close();
	});
    }
}
