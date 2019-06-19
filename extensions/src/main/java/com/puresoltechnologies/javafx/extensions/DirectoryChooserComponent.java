package com.puresoltechnologies.javafx.extensions;

import java.io.File;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

/**
 * This component is a convenience implementation of a file chooser component
 * for dialogs and forms. The file chooser component contains of a
 * {@link Label}, a {@link TextField} and also a Choose {@link ButtonType}.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class DirectoryChooserComponent extends AbstractChooserComponent<File> {

    public DirectoryChooserComponent() {
	super();
    }

    public DirectoryChooserComponent(String title) {
	super(title);
    }

    @Override
    protected String callChooserDialog() {
	DirectoryChooser directoryChooser = new DirectoryChooser();
	directoryChooser.setTitle(getTitle());
	File file = directoryChooser.showDialog(getScene().getWindow());
	return file != null ? file.getPath() : "";
    }

    @Override
    protected File convertResult(String text) {
	return new File(text);
    }
}
