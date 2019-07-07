package com.puresoltechnologies.javafx.extensions.dialogs.procedure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.puresoltechnologies.javafx.extensions.StatusBar;
import com.puresoltechnologies.javafx.extensions.dialogs.DialogHeader;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

/**
 * This class provides an interactive, guided and visual way to lead users
 * through procedures.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class ProcedureWizard<T> extends Dialog<T> {

    private final List<Step<T>> steps = new ArrayList<>();

    private final DialogHeader dialogHeader = new DialogHeader();

    private final BorderPane inputPane = new BorderPane();
    private final BorderPane visualizationPane = new BorderPane();
    private final SplitPane contentPane = new SplitPane(inputPane, visualizationPane);
    private final ProgressBar progress = new ProgressBar();

    private final Button resetButton = new Button("Reset");
    private final Button undoButton = new Button("Undo");
    private final Button applyButton = new Button("Apply");

    public ProcedureWizard(Supplier<T> supplier) {
	super();
	setResizable(true);
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
	ButtonBar inputButtonBar = new ButtonBar();
	inputButtonBar.getButtons().addAll(resetButton, undoButton, applyButton);

	inputPane.setBottom(inputButtonBar);

	progress.setMaxWidth(Double.MAX_VALUE);
	progress.setProgress(0.5);
	progress.setProgress(0.5);
	progress.setTooltip(new Tooltip("Shows the overall progress of this procedure."));

	BorderPane borderPane = new BorderPane();
	borderPane.setPadding(Insets.EMPTY);
	borderPane.setTop(dialogHeader);
	borderPane.setCenter(contentPane);
	borderPane.setBottom(progress);

	DialogPane dialogPane = getDialogPane();
	dialogPane.setContent(borderPane);
	dialogPane.getButtonTypes().addAll(ButtonType.PREVIOUS, ButtonType.NEXT, ButtonType.CANCEL, ButtonType.FINISH);
    }

}
