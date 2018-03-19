package com.puresoltechnologies.javafx.perspectives.menu;

import java.io.IOException;

import com.puresoltechnologies.javafx.perspectives.PerspectiveService;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResetPerspectiveMenuItem extends MenuItem {

    private static final Image icon;
    static {
	try {
	    icon = ResourceUtils.getImage(ResetPerspectiveMenuItem.class, "/icons/FatCow_Icons16x16/undo.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public ResetPerspectiveMenuItem() {
	super("Reset Perspective", new ImageView(icon));
	initialize();
    }

    public ResetPerspectiveMenuItem(String text) {
	super(text);
	initialize();
    }

    public ResetPerspectiveMenuItem(String text, Node graphic) {
	super(text, graphic);
	initialize();
    }

    private void initialize() {
	setOnAction(event -> {
	    PerspectiveService.resetPerspective();
	    event.consume();
	});
    }

}
