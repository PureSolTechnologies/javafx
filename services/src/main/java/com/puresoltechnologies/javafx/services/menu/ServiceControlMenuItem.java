package com.puresoltechnologies.javafx.services.menu;

import java.io.IOException;

import com.puresoltechnologies.javafx.extensions.StatusBar;
import com.puresoltechnologies.javafx.services.dialogs.ServicesControlDialog;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ServiceControlMenuItem extends MenuItem {

    private static final Image icon;
    static {
	try {
	    icon = ResourceUtils.getImage(StatusBar.class, "icons/FatCow_Icons16x16/information.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public ServiceControlMenuItem() {
	super("Service Control...", new ImageView(icon));
	initialize();
    }

    public ServiceControlMenuItem(String text) {
	super(text);
	initialize();
    }

    public ServiceControlMenuItem(String text, Node graphic) {
	super(text, graphic);
	initialize();
    }

    private void initialize() {
	setOnAction(event -> {
	    new ServicesControlDialog().showAndWait();
	    event.consume();
	});
    }

}
