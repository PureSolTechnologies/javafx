package com.puresoltechnologies.javafx.extensions;

import java.io.File;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * This component is a convenience implementation of a file chooser component
 * for dialogs and forms. The file chooser component contains of a
 * {@link Label}, a {@link TextField} and also a Choose {@link ButtonType}.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class FileChooserComponent extends AbstractChooserComponent<File> {

    public FileChooserComponent() {
	super();
    }

    public FileChooserComponent(String title) {
	super(title);
    }

    @Override
    protected String callChooserDialog() {
	FileChooser fileChooser = new FileChooser();
	fileChooser.setTitle(getTitle());
	File file = fileChooser.showOpenDialog(getScene().getWindow());
	return file != null ? file.getPath() : "";
    }

    @Override
    protected File convertResult(String text) {
	return new File(text);
    }
}