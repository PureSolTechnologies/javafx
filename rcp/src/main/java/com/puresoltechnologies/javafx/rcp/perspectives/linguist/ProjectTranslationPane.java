/****************************************************************************
 *
 *   ProjectTranslationPanel.java
 *   -------------------
 *   copyright            : (c) 2009-2011 by PureSol-Technologies
 *   author               : Rick-Rainer Ludwig
 *   email                : ludwig@puresol-technologies.com
 *
 ****************************************************************************/

/****************************************************************************
 *
 * Copyright 2009-2011 PureSol-Technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ****************************************************************************/

package com.puresoltechnologies.javafx.rcp.perspectives.linguist;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import com.puresoltechnologies.javafx.i18n.LocaleChooser;
import com.puresoltechnologies.javafx.i18n.Translator;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;

/**
 * This class provides a complete panel for editing translations for a whole
 * project. The language can be chosen to be edited and everything else is
 * delegate to the LanguageTranslationPanel.
 *
 * @see FilesTranslationPanel
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class ProjectTranslationPane extends BorderPane {

    private static final Translator translator = Translator.getTranslator(ProjectTranslationPane.class);

    // GUI elements...
    private final LocaleChooser localeChooser = new LocaleChooser();
    private final FilesTranslationPanel translationPanel = new FilesTranslationPanel();

    /**
     * This is the default constructor. The panel is empty and a project directory
     * needs to be opened afterwards.
     *
     * @see openDirectory
     */
    public ProjectTranslationPane() {
	super();
	initUI();
    }

    /**
     * This constructor opens the project directory during initialization and
     * provides a non empty panel at startup.
     *
     * @param directory is the project directory to be opened.
     */
    public ProjectTranslationPane(File directory) {
	this();
	openDirectory(directory);
    }

    /**
     * This method initializes the UI.
     */
    private void initUI() {
	localeChooser.setValue(Locale.getDefault());
	localeChooser.valueProperty().addListener((oldValue, newValue, component) -> {
	    try {
		translationPanel.setSelectedLocale(newValue);
	    } catch (IOException e) {
		Alert alert = new Alert(AlertType.ERROR,
			translator.i18n("The panel could not be updated for the new language!"), ButtonType.OK);
		alert.showAndWait();
	    }
	});

	setTop(new TitledPane("Language", localeChooser));
	setCenter(translationPanel);
    }

    /**
     * This method checks whether the content of the panel was changed or not.
     *
     * @return True is returned if there are some changes to save.
     */
    public boolean hasChanged() {
	return translationPanel.hasChanged();
    }

    /**
     * This method saves the currently opened file for editing.
     *
     * @return
     * @throws IOException
     */
    public void saveFile() throws IOException {
	translationPanel.saveFile();
    }

    public void openDirectory(File directory) {
	translationPanel.openDirectory(directory);
    }

    public File getDirectory() {
	return translationPanel.getDirectory();
    }

    public void removeObsoletePhrases() {
	translationPanel.removeObsoletePhrases();
    }

}
