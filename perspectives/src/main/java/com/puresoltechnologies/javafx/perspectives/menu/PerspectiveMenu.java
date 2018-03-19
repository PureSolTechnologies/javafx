package com.puresoltechnologies.javafx.perspectives.menu;

import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class PerspectiveMenu extends Menu {

    public PerspectiveMenu() {
	super("Perspective");
	initialize();
    }

    public PerspectiveMenu(String text) {
	super(text);
	initialize();
    }

    public PerspectiveMenu(String text, Node graphic) {
	super(text, graphic);
	initialize();
    }

    public PerspectiveMenu(String text, Node graphic, MenuItem... items) {
	super(text, graphic, items);
	initialize();
    }

    private void initialize() {
	OpenPerspectiveMenuItem openPerspectiveMenuItem = new OpenPerspectiveMenuItem();
	ResetPerspectiveMenuItem resetPerspectiveMenuItem = new ResetPerspectiveMenuItem();
	ClosePerspectiveMenuItem closePerspectiveMenuItem = new ClosePerspectiveMenuItem();
	CloseAllPerspectivesMenuItem closeAllPerspectivesMenuItem = new CloseAllPerspectivesMenuItem();
	getItems().addAll(openPerspectiveMenuItem, resetPerspectiveMenuItem, closePerspectiveMenuItem,
		closeAllPerspectivesMenuItem);
    }

}
