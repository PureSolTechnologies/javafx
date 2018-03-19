package com.puresoltechnologies.javafx.perspectives.menu;

import java.io.IOException;
import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.PerspectiveService;
import com.puresoltechnologies.javafx.perspectives.dialogs.ViewSelectionDialog;
import com.puresoltechnologies.javafx.perspectives.parts.ViewerPart;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShowViewMenuItem extends MenuItem {

    private static final Image icon;
    static {
	try {
	    icon = ResourceUtils.getImage(ShowViewMenuItem.class, "/icons/FatCow_Icons16x16/watch_window.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public ShowViewMenuItem() {
	super("Show View...", new ImageView(icon));
	initialize();
    }

    public ShowViewMenuItem(String text) {
	super(text);
	initialize();
    }

    public ShowViewMenuItem(String text, Node graphic) {
	super(text, graphic);
	initialize();
    }

    private void initialize() {
	setOnAction(event -> {
	    Optional<ViewerPart> part = new ViewSelectionDialog().showAndWait();
	    if (part.isPresent()) {
		PerspectiveService.showPart(part.get());
	    }
	    event.consume();
	});
    }

}
