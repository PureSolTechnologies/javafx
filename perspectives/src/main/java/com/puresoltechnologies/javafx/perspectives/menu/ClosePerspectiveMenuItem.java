package com.puresoltechnologies.javafx.perspectives.menu;

import java.io.IOException;

import com.puresoltechnologies.javafx.perspectives.PerspectiveService;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ClosePerspectiveMenuItem extends MenuItem {

    private static final Image icon;
    static {
	try {
	    icon = ResourceUtils.getImage(ClosePerspectiveMenuItem.class, "/icons/FatCow_Icons16x16/cross.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public ClosePerspectiveMenuItem() {
	super("Close Perspective", new ImageView(icon));
	initialize();
    }

    public ClosePerspectiveMenuItem(String text) {
	super(text);
	initialize();
    }

    public ClosePerspectiveMenuItem(String text, Node graphic) {
	super(text, graphic);
	initialize();
    }

    private void initialize() {
	setOnAction(event -> {
	    PerspectiveService.closePerspective();
	    event.consume();
	});
    }

}
