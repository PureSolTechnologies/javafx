package com.puresoltechnologies.javafx.perspectives.menu;

import java.io.IOException;
import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.Perspective;
import com.puresoltechnologies.javafx.perspectives.PerspectiveService;
import com.puresoltechnologies.javafx.perspectives.dialogs.PerspectiveSelectionDialog;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OpenPerspectiveMenuItem extends MenuItem {

    private static final Image icon;
    static {
	try {
	    icon = ResourceUtils.getImage(OpenPerspectiveMenuItem.class, "/icons/FatCow_Icons16x16/switch_windows.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public OpenPerspectiveMenuItem() {
	super("Open Perspective...", new ImageView(icon));
	initialize();
    }

    public OpenPerspectiveMenuItem(String text) {
	super(text);
	initialize();
    }

    public OpenPerspectiveMenuItem(String text, Node graphic) {
	super(text, graphic);
	initialize();
    }

    private void initialize() {
	setOnAction(event -> {
	    Optional<Perspective> perspective = new PerspectiveSelectionDialog().showAndWait();
	    if (perspective.isPresent()) {
		PerspectiveService.openPerspective(perspective.get());
	    }
	    event.consume();
	});
    }

}
