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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.puresoltechnologies.javafx.i18n.LocaleChooser;
import com.puresoltechnologies.javafx.i18n.Translator;

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
class ProjectTranslationPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private static final Translator translator = Translator.getTranslator(ProjectTranslationPanel.class);

    private final TitledBorder languageBorder = BorderFactory.createTitledBorder("Language");

    // GUI elements...
    private final LocaleChooser localeChooser = new LocaleChooser();
    private final FilesTranslationPanel translationPanel = new FilesTranslationPanel();

    /**
     * This is the default constructor. The panel is empty and a project directory
     * needs to be opened afterwards.
     *
     * @see openDirectory
     */
    public ProjectTranslationPanel() {
	super();
	initUI();
    }

    /**
     * This constructor opens the project directory during initialization and
     * provides a non empty panel at startup.
     *
     * @param directory is the project directory to be opened.
     */
    public ProjectTranslationPanel(File directory) {
	this();
	openDirectory(directory);
    }

    /**
     * This method initializes the UI.
     */
    private void initUI() {
	BorderLayout borderLayout = new BorderLayout();
	borderLayout.setHgap(5);
	borderLayout.setVgap(5);
	setLayout(borderLayout);

	localeChooser.setSelectedLocale(Locale.getDefault());
	localeChooser.setBorder(languageBorder);
	localeChooser.addActionListener(this);

	add(localeChooser, BorderLayout.NORTH);
	add(translationPanel, BorderLayout.CENTER);
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

    @Override
    public void actionPerformed(ActionEvent o) {
	if (o.getSource() == localeChooser) {
	    try {
		translationPanel.setSelectedLocale(localeChooser.getSelectedLocale());
	    } catch (IOException e) {
		JOptionPane.showMessageDialog(getParent(),
			translator.i18n("The panel could not be updated for the new language!"),
			translator.i18n("Error"), JOptionPane.ERROR_MESSAGE);
	    }
	}
    }

    public void removeObsoletePhrases() {
	translationPanel.removeObsoletePhrases();
    }

}
