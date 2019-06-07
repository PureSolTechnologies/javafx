package com.puresoltechnologies.javafx.perspectives.menu;

import java.io.IOException;

import com.puresoltechnologies.javafx.perspectives.Perspective;
import com.puresoltechnologies.javafx.perspectives.tasks.OpenPartTask;
import com.puresoltechnologies.javafx.utils.FXThreads;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShowPartMenuItem extends MenuItem {

    private static final Image icon;
    static {
	try {
	    icon = ResourceUtils.getImage(Perspective.class, "icons/FatCow_Icons16x16/watch_window.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public ShowPartMenuItem() {
	super("Show _Part...", new ImageView(icon));
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
	    FXThreads.runOnFXThread(new OpenPartTask());
	    event.consume();
	});
    }

}
