package com.puresoltechnologies.javafx.preferences.menu;

import java.io.IOException;

import com.puresoltechnologies.javafx.preferences.Preferences;
import com.puresoltechnologies.javafx.preferences.dialogs.PreferencesDialog;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PreferencesMenuItem extends MenuItem {

    private static final Image icon;
    static {
	try {
	    icon = ResourceUtils.getImage(Preferences.class, "icons/FatCow_Icons16x16/setting_tools.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public PreferencesMenuItem() {
	super("_Preferences...", new ImageView(icon));
	initialize();
    }

    public PreferencesMenuItem(String text, Node graphic) {
	super(text, graphic);
	initialize();
    }

    public PreferencesMenuItem(String text) {
	super(text);
	initialize();
    }

    private void initialize() {
	setOnAction(event -> {
	    new PreferencesDialog().showAndWait();
	    event.consume();
	});
    }
}
