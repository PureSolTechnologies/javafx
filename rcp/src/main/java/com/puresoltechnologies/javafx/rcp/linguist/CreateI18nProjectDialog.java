package com.puresoltechnologies.javafx.rcp.linguist;

import com.puresoltechnologies.javafx.extensions.FileChooserComponent;
import com.puresoltechnologies.javafx.i18n.LocaleChooser;
import com.puresoltechnologies.javafx.i18n.Translator;
import com.puresoltechnologies.javafx.i18n.proc.I18NProjectConfiguration;

import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

/**
 * This dialog is used to create a new I18n project.
 *
 * @author Rick-Rainer Ludwig
 */
public class CreateI18nProjectDialog extends Dialog<I18NProjectConfiguration> {

    private static final Translator translator = Translator.getTranslator(CreateI18nProjectDialog.class);

    private final FileChooserComponent projectFileComponent = new FileChooserComponent("Project File");
    private final FileChooserComponent sourceDirectoryComponent = new FileChooserComponent("Source Directory");
    private final FileChooserComponent resourceDirectoryComponent = new FileChooserComponent("Target Directory");
    private final LocaleChooser implementationLocaleChooser = new LocaleChooser();

    public CreateI18nProjectDialog() {
	super();
	setTitle(translator.i18n("Create new I18n project"));
	setResizable(true);

	TitledPane titledPane = new TitledPane("Project File", projectFileComponent);

	VBox directoriesPane = new VBox();

	directoriesPane.getChildren().addAll(sourceDirectoryComponent, resourceDirectoryComponent);

	TitledPane sourceDirectories = new TitledPane("Directories", directoriesPane);
	TitledPane settings = new TitledPane("Settings", implementationLocaleChooser);

	VBox vbox = new VBox();
	vbox.getChildren().addAll(titledPane, sourceDirectories, settings);

	DialogPane dialogPane = getDialogPane();
	dialogPane.setContent(vbox);

	ObservableList<ButtonType> buttonTypes = dialogPane.getButtonTypes();
	buttonTypes.addAll(ButtonType.OK, ButtonType.CANCEL);
    }

}
