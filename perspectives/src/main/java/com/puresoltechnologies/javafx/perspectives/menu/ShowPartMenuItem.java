package com.puresoltechnologies.javafx.perspectives.menu;

import java.io.IOException;
import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.PerspectiveService;
import com.puresoltechnologies.javafx.perspectives.dialogs.PartSelectionDialog;
import com.puresoltechnologies.javafx.perspectives.parts.Part;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShowPartMenuItem extends MenuItem {

    private static final Image icon;
    static {
	try {
	    icon = ResourceUtils.getImage(ShowPartMenuItem.class, "/icons/FatCow_Icons16x16/watch_window.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public ShowPartMenuItem() {
	super("Show Part...", new ImageView(icon));
	initialize();
    }

    public ShowPartMenuItem(String text) {
	super(text);
	initialize();
    }

    public ShowPartMenuItem(String text, Node graphic) {
	super(text, graphic);
	initialize();
    }

    private void initialize() {
	setOnAction(event -> {
	    Optional<Part> part = new PartSelectionDialog().showAndWait();
	    if (part.isPresent()) {
		PerspectiveService.openPart(part.get());
		part.get().manualInitialization();
	    }
	    event.consume();
	});
    }

}
