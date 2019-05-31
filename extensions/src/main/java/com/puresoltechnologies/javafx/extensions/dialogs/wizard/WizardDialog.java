package com.puresoltechnologies.javafx.extensions.dialogs.wizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.puresoltechnologies.javafx.extensions.dialogs.DialogHeader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

/**
 * This class provides a classical Wizard Dialog which leads the user through
 * several steps of a process.
 *
 * @author Rick-Rainer Ludwig
 *
 * @param <T>
 */
public class WizardDialog<T> extends Dialog<T> {

    private final List<WizardPage<T>> pages = new ArrayList<>();

    private final ObservableList<String> stepTitleList = FXCollections.observableArrayList();
    private final ListView<String> stepListView = new ListView<>(stepTitleList);
    private int currentPageId = -1;

    private final DialogHeader header = new DialogHeader();
    private final Button previousButton;
    private final Button nextButton;
    private final Button finishButton;
    private final Button cancelButton;

    private final BorderPane content = new BorderPane();

    private final T data;

    public WizardDialog(Supplier<T> supplier) {
	super();
	this.data = supplier.get();
	setResizable(true);

	stepListView.setEditable(false);
	stepListView.setDisable(true);

	content.setTop(header);
	content.setLeft(stepListView);

	DialogPane dialogPane = getDialogPane();
	dialogPane.setContent(content);
	dialogPane.setPadding(new Insets(0));

	ObservableList<ButtonType> buttonTypes = dialogPane.getButtonTypes();
	buttonTypes.addAll(ButtonType.PREVIOUS, ButtonType.NEXT, ButtonType.FINISH, ButtonType.CANCEL);
	previousButton = (Button) dialogPane.lookupButton(ButtonType.PREVIOUS);
	previousButton.addEventFilter(ActionEvent.ACTION, event -> goBack(event));
	nextButton = (Button) dialogPane.lookupButton(ButtonType.NEXT);
	nextButton.addEventFilter(ActionEvent.ACTION, event -> goForward(event));
	finishButton = (Button) dialogPane.lookupButton(ButtonType.FINISH);
	finishButton.addEventFilter(ActionEvent.ACTION, event -> finish(event));
	cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
	cancelButton.addEventFilter(ActionEvent.ACTION, event -> cancel(event));
    }

    private void goBack(ActionEvent event) {
	setCurrentPage(currentPageId - 1);
	event.consume();
    }

    private void goForward(ActionEvent event) {
	setCurrentPage(currentPageId + 1);
	event.consume();
    }

    private void finish(ActionEvent event) {
	setResult(data);
	event.consume();
    }

    private void cancel(ActionEvent event) {
	setResult(null);
	event.consume();
    }

    public final void addPage(WizardPage<T> page) {
	page.setData(data);
	pages.add(page);
	stepTitleList.add(page.getTitle());
	if (pages.size() == 1) {
	    setCurrentPage(0);
	} else {
	    nextButton.setVisible(true);
	}
    }

    private void setCurrentPage(int pageId) {
	WizardPage<T> page = pages.get(pageId);
	if (pageId == 0) {
	    previousButton.setVisible(false);
	} else {
	    previousButton.setVisible(true);
	}
	if (pageId == (pages.size() - 1)) {
	    nextButton.setVisible(false);
	} else {
	    nextButton.setVisible(true);
	}
	nextButton.disableProperty().unbind();
	nextButton.disableProperty().bind(page.canProceedProperty().not());
	finishButton.disableProperty().unbind();
	finishButton.disableProperty().bind(page.canFinishProperty().not());

	content.setCenter(page.getNode());
	stepListView.getSelectionModel().select(pageId);
	currentPageId = pageId;
	setDialogHeader(page);
    }

    private void setDialogHeader(WizardPage<T> page) {
	header.setTitle(page.getTitle());
	Optional<String> description = page.getDescription();
	if (description.isPresent()) {
	    header.setDescription(description.get());
	}
	Optional<Image> image = page.getImage();
	if (image.isPresent()) {
	    header.setImage(image.get());
	}
    }

    public void test() {
	show();
    }
}
