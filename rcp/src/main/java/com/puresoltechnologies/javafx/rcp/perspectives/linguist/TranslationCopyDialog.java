package com.puresoltechnologies.javafx.rcp.perspectives.linguist;

import java.util.Locale;

import com.puresoltechnologies.javafx.i18n.LocaleChooser;
import com.puresoltechnologies.javafx.i18n.Translator;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class TranslationCopyDialog extends Dialog<Void> {

    private static final Translator translator = Translator.getTranslator(TranslationCopyDialog.class);

    private final LocaleChooser sourceTranslations = new LocaleChooser();
    private final LocaleChooser targetTranslations = new LocaleChooser();

    private final boolean finishedByOK = false;

    public TranslationCopyDialog() {
	super();
	setTitle(translator.i18n("Copy Translation"));
	initUI();
    }

    private void initUI() {
	DialogPane dialogPane = getDialogPane();

	BorderPane pane = new BorderPane();
	dialogPane.setContent(pane);

	dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

	HBox listPane = new HBox();
	listPane.getChildren().addAll(new Label(translator.i18n("from:")), sourceTranslations,
		new Label(translator.i18n("to:")), targetTranslations);
	pane.setCenter(listPane);
    }

    public boolean isFinishedByOK() {
	return finishedByOK;
    }

    public Locale getSource() {
	return sourceTranslations.getValue();
    }

    public Locale getTarget() {
	return targetTranslations.getValue();
    }

}
