package com.puresoltechnologies.javafx.perspectives.menu;

import java.io.IOException;

import com.puresoltechnologies.javafx.perspectives.Perspective;
import com.puresoltechnologies.javafx.perspectives.PerspectiveService;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CloseAllPerspectivesMenuItem extends MenuItem {

    private static final Image icon;
    static {
	try {
	    icon = ResourceUtils.getImage(Perspective.class, "icons/FatCow_Icons16x16/delete.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public CloseAllPerspectivesMenuItem() {
	super("Close All Perspectives", new ImageView(icon));
	initialize();
    }

    public CloseAllPerspectivesMenuItem(String text) {
	super(text);
	initialize();
    }

    public CloseAllPerspectivesMenuItem(String text, Node graphic) {
	super(text, graphic);
	initialize();
    }

    private void initialize() {
	setOnAction(event -> {
	    PerspectiveService.closeAllPerspectives();
	    event.consume();
	});
    }

}
