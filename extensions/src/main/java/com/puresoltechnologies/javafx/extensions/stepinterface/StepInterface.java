package com.puresoltechnologies.javafx.extensions.stepinterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.puresoltechnologies.javafx.extensions.StatusBar;
import com.puresoltechnologies.javafx.extensions.dialogs.DialogHeader;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * This class provides an interactive, guided and visual way to lead users
 * through procedures.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class StepInterface<T> extends BorderPane {

    private final List<Step<T>> steps = new ArrayList<>();

    private final DialogHeader dialogHeader = new DialogHeader();
    private final GridPane buttonPanel = new GridPane();

    private final BorderPane inputPane = new BorderPane();
    private final BorderPane visualizationPane = new BorderPane();
    private final SplitPane contentPane = new SplitPane(inputPane, visualizationPane);

    private final Button resetButton = new Button("Reset");
    private final Button undoButton = new Button("Undo");
    private final Button applyButton = new Button("Apply");
    private final ButtonBar inputButtonBar = new ButtonBar();

    private final Button backButton = new Button("< Back");
    private final Button nextButton = new Button("Next >");
    private final Button finishButton = new Button("Finish");
    private final ButtonBar progressButtonBar = new ButtonBar();

    public StepInterface() {
	super();

	Image image = null;
	try {
	    image = ResourceUtils.getImage(StatusBar.class, "icons/FatCow_Icons32x32/information.png");
	} catch (IOException e) {
	    e.printStackTrace();
	}

	dialogHeader.setTitle("Title");
	dialogHeader.setDescription("This is the description...");
	if (image != null) {
	    dialogHeader.setImage(image);
	}

	ButtonBar.setButtonData(resetButton, ButtonData.OTHER);
	ButtonBar.setButtonData(undoButton, ButtonData.APPLY);
	ButtonBar.setButtonData(applyButton, ButtonData.APPLY);
	inputButtonBar.getButtons().addAll(resetButton, undoButton, applyButton);

	ButtonBar.setButtonData(backButton, ButtonData.BACK_PREVIOUS);
	ButtonBar.setButtonData(nextButton, ButtonData.BACK_PREVIOUS);
	ButtonBar.setButtonData(finishButton, ButtonData.FINISH);
	progressButtonBar.getButtons().addAll(backButton, nextButton, finishButton);

	inputPane.setBottom(inputButtonBar);
	visualizationPane.setBottom(progressButtonBar);

	setTop(dialogHeader);
	setCenter(contentPane);
	setBottom(buttonPanel);
    }

}
