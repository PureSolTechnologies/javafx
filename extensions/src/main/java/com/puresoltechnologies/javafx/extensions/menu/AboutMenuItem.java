package com.puresoltechnologies.javafx.extensions.menu;

import java.io.IOException;

import com.puresoltechnologies.javafx.extensions.StatusBar;
import com.puresoltechnologies.javafx.extensions.dialogs.AboutDialog;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AboutMenuItem extends MenuItem {

    private static final Image icon;
    static {
	try {
	    icon = ResourceUtils.getImage(StatusBar.class, "icons/FatCow_Icons16x16/information.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public AboutMenuItem() {
	super("About...", new ImageView(icon));
	initialize();
    }

    public AboutMenuItem(String text) {
	super(text);
	initialize();
    }

    public AboutMenuItem(String text, Node graphic) {
	super(text, graphic);
	initialize();
    }

    private void initialize() {
	setOnAction(event -> {
	    new AboutDialog().showAndWait();
	    event.consume();
	});
    }

}
