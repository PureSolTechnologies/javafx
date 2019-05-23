package com.puresoltechnologies.javafx.extensions.dialogs.wizard;

import java.util.ArrayList;
import java.util.List;

import com.puresoltechnologies.javafx.extensions.dialogs.DialogHeader;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

/**
 * This class provides a classical Wizard Dialog which leads the user through
 * several steps of a process.
 *
 * @author Rick-
 *
 * @param <T>
 */
public class WizardDialog<T> extends Dialog<T> {

    private final List<WizardPage<T>> pages = new ArrayList<>();

    private final DialogHeader header = new DialogHeader();
    private final ButtonBar buttonBar = new ButtonBar();
    private final ListView<String> stepList = new ListView<>();

    private final ButtonType nextButton = new ButtonType("Next >", ButtonData.BACK_PREVIOUS);
    private final ButtonType backButton = new ButtonType("< Back", ButtonData.BACK_PREVIOUS);
    private final ButtonType finishButton = new ButtonType("Finish", ButtonData.FINISH);
    private final ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

    private final BorderPane content = new BorderPane();

    public WizardDialog() {
	super();
	setResizable(true);

	content.setTop(header);
	content.setLeft(stepList);
	content.setBottom(buttonBar);

	DialogPane dialogPane = getDialogPane();
	dialogPane.setContent(content);
	dialogPane.setPadding(new Insets(0));

	ObservableList<ButtonType> buttonTypes = dialogPane.getButtonTypes();
	buttonTypes.addAll(backButton, nextButton, finishButton, cancelButton);
    }

}
