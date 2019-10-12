package com.puresoltechnologies.javafx.extensions.menu;

import java.io.IOException;

import com.puresoltechnologies.javafx.extensions.StatusBar;
import com.puresoltechnologies.javafx.extensions.dialogs.about.AboutDialog;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.application.Application;
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

    private final Application application;

    /**
     * This is the default value constructor.
     *
     * @param application is a reference to the application for which the about
     *                    dialog is to be shown.
     */
    public AboutMenuItem(Application application) {
	super("About...", new ImageView(icon));
	this.application = application;
	initialize();
    }

    public AboutMenuItem(Application application, String text) {
	super(text);
	this.application = application;
	initialize();
    }

    public AboutMenuItem(Application application, String text, Node graphic) {
	super(text, graphic);
	this.application = application;
	initialize();
    }

    private void initialize() {
	setOnAction(event -> {
	    new AboutDialog(application).showAndWait();
	    event.consume();
	});
    }

}
