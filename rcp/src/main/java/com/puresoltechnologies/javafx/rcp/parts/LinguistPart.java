package com.puresoltechnologies.javafx.rcp.parts;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.puresoltechnologies.javafx.i18n.LanguageDialog;
import com.puresoltechnologies.javafx.i18n.Translator;
import com.puresoltechnologies.javafx.i18n.data.I18NFile;
import com.puresoltechnologies.javafx.i18n.data.LanguageSet;
import com.puresoltechnologies.javafx.i18n.data.MultiLanguageTranslations;
import com.puresoltechnologies.javafx.i18n.proc.I18NProjectConfiguration;
import com.puresoltechnologies.javafx.i18n.proc.I18NRelease;
import com.puresoltechnologies.javafx.i18n.proc.I18NUpdate;
import com.puresoltechnologies.javafx.i18n.utils.FileSearch;
import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.parts.AbstractViewer;
import com.puresoltechnologies.javafx.perspectives.parts.PartOpenMode;
import com.puresoltechnologies.javafx.rcp.Rcp;
import com.puresoltechnologies.javafx.rcp.linguist.CreateI18nProjectDialog;
import com.puresoltechnologies.javafx.rcp.linguist.ProjectTranslationPane;
import com.puresoltechnologies.javafx.rcp.linguist.TranslationCopyDialog;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;

public class LinguistPart extends AbstractViewer {

    private static final Translator translator = Translator.getTranslator(LinguistPart.class);

    // other GUI elements...
    private final ProjectTranslationPane translationPane = new ProjectTranslationPane();

    private final BorderPane borderPane = new BorderPane();

    public LinguistPart() {
	super("Linguist", PartOpenMode.AUTO_AND_MANUAL);
	try {
	    setImage(ResourceUtils.getImage(Rcp.class, "icons/FatCow_Icons16x16/change_language.png"));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public Optional<PartHeaderToolBar> getToolBar() {
	return Optional.empty();
    }

    @Override
    public void initialize() {
	initializeVariables();
	initializeMenu();
	initializeDesktop();
	initializeToolBar();
    }

    @Override
    public void close() {
	// TODO Auto-generated method stub
    }

    @Override
    public Node getContent() {
	return borderPane;
    }

    private void initializeVariables() {

    }

    private void initializeMenu() {
	Menu fileMenu = new Menu("_File");

	MenuItem create = new MenuItem("_Create...");
	create.setOnAction(event -> {
	    create();
	    event.consume();
	});
	MenuItem open = new MenuItem("_Open...");
	open.setOnAction(event -> {
	    open();
	    event.consume();
	});
	open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
	MenuItem save = new MenuItem("_Save...");
	save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
	save.setOnAction(event -> {
	    save();
	    event.consume();
	});

	fileMenu.getItems().addAll(create, open, save);

	Menu toolsMenu = new Menu("_Tools");

	MenuItem update = new MenuItem("Update");
	update.setOnAction(event -> {
	    update();
	    event.consume();
	});
	MenuItem release = new MenuItem("Release");
	release.setOnAction(event -> {
	    release();
	    event.consume();
	});
	MenuItem clear = new MenuItem("Clear");
	clear.setOnAction(event -> {
	    clear();
	    event.consume();
	});
	MenuItem copyTranslation = new MenuItem("Copy Translation...");
	copyTranslation.setOnAction(event -> {
	    copyTranslation();
	    event.consume();
	});

	toolsMenu.getItems().addAll(update, release, clear, copyTranslation);

	Menu optionsMenu = new Menu("_Options");

	MenuItem language = new MenuItem("Language...");
	language.setOnAction(event -> {
	    setLanguage();
	    event.consume();
	});

	optionsMenu.getItems().add(language);

	Menu helpMenu = new Menu("_Help");

	MenuBar menuBar = new MenuBar(fileMenu, toolsMenu, optionsMenu, helpMenu);
	borderPane.setTop(menuBar);
    }

    private void initializeDesktop() {
	borderPane.setCenter(translationPane);
    }

    private void initializeToolBar() {
	Button createButton = new Button("Create...");
	createButton.setOnAction(event -> {
	    create();
	    event.consume();
	});
	Button openButton = new Button("Open...");
	openButton.setOnAction(event -> {
	    open();
	    event.consume();
	});
	Button updateButton = new Button("Update");
	updateButton.setOnAction(event -> {
	    update();
	    event.consume();
	});
	Button releaseButton = new Button("Release");
	releaseButton.setOnAction(event -> {
	    release();
	    event.consume();
	});
	Button clearButton = new Button("Clear");
	clearButton.setOnAction(event -> {
	    clear();
	    event.consume();
	});

	ToolBar tools = new ToolBar();
	tools.getItems().addAll(createButton, openButton, updateButton, releaseButton, clearButton);
	borderPane.setTop(tools);
    }

    private void update() {
	try {
	    I18NUpdate.update(translationPane.getDirectory());
	} catch (IOException e) {
	    e.printStackTrace();
	    Alert alert = new Alert(AlertType.ERROR,
		    translator.i18n("IO error during update!\n\nMessage was:\n{0}", e.getMessage()), ButtonType.OK);
	    alert.showAndWait();
	}
    }

    private void release() {
	try {
	    I18NRelease.release(translationPane.getDirectory());
	} catch (IOException e) {
	    e.printStackTrace();
	    Alert alert = new Alert(AlertType.ERROR,
		    translator.i18n("IO error during release!\n\nMessage was:\n{0}", e.getMessage()), ButtonType.OK);
	    alert.showAndWait();
	}
    }

    private void clear() {
	translationPane.removeObsoletePhrases();
    }

    private void copyTranslation() {
	try {
	    TranslationCopyDialog dlg = new TranslationCopyDialog();
	    dlg.showAndWait();
	    if (dlg.isFinishedByOK()) {
		System.out.println("Copy from " + dlg.getSource() + " to " + dlg.getTarget() + "...");
		Locale sourceLocale = dlg.getSource();
		Locale targetLocale = dlg.getTarget();
		I18NProjectConfiguration configuration;
		configuration = new I18NProjectConfiguration(translationPane.getDirectory());
		List<File> files = FileSearch.find(configuration.getI18nDirectory(), "*.i18n");
		for (File file : files) {
		    File i18nFile = new File(configuration.getI18nDirectory(), file.getPath());
		    MultiLanguageTranslations translations = I18NFile.read(i18nFile);
		    for (String source : translations.getSources()) {
			LanguageSet languageSet = translations.getTranslations(source);
			if (languageSet.has(sourceLocale)) {
			    translations.add(source, targetLocale, languageSet.get(sourceLocale));
			}
		    }
		    I18NFile.write(i18nFile, translations);
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	    Alert alert = new Alert(AlertType.ERROR,
		    translator.i18n("Could not copy translations due to IO error!\n\nMessage: {0}", e.getMessage()),
		    ButtonType.OK);
	    alert.showAndWait();
	}
    }

    private void create() {
	CreateI18nProjectDialog dialog = new CreateI18nProjectDialog();
	dialog.showAndWait();
    }

    private void open() {
	DirectoryChooser directoryChooser = new DirectoryChooser();
	directoryChooser.setTitle("Open Resource File");
	File directory = directoryChooser.showDialog(borderPane.getScene().getWindow());
	if (directory == null) {
	    return;
	}
	translationPane.openDirectory(directory);
    }

    private void save() {
	try {
	    translationPane.saveFile();
	} catch (IOException e) {
	    e.printStackTrace();
	    Alert alert = new Alert(AlertType.ERROR, translator.i18n("I18NFile could not be saved!"), ButtonType.OK);
	    alert.showAndWait();
	}
    }

    private void setLanguage() {
	new LanguageDialog().showAndWait();
    }

}
