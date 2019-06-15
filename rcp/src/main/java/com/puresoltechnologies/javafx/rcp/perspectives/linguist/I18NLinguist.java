/****************************************************************************
 *
 *   I18NLinguist.java
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import com.puresoltechnologies.javafx.i18n.KeyStrokeUpdater;
import com.puresoltechnologies.javafx.i18n.LanguageDialog;
import com.puresoltechnologies.javafx.i18n.TranslationUpdater;
import com.puresoltechnologies.javafx.i18n.Translator;
import com.puresoltechnologies.javafx.i18n.data.I18NFile;
import com.puresoltechnologies.javafx.i18n.data.LanguageSet;
import com.puresoltechnologies.javafx.i18n.data.MultiLanguageTranslations;
import com.puresoltechnologies.javafx.i18n.proc.I18NProjectConfiguration;
import com.puresoltechnologies.javafx.i18n.proc.I18NRelease;
import com.puresoltechnologies.javafx.i18n.proc.I18NUpdate;
import com.puresoltechnologies.javafx.i18n.utils.FileSearch;

/**
 * I18NLinguist is the base application for the whole I18N framework. All non
 * programming related activities can be controlled and performed here.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class I18NLinguist extends JFrame implements ActionListener, WindowListener {

    private static final long serialVersionUID = 1L;

    private static final Translator translator = Translator.getTranslator(I18NLinguist.class);

    private final TranslationUpdater translationUpdater = new TranslationUpdater();
    private final KeyStrokeUpdater keyStrokeUpdater = new KeyStrokeUpdater();

    // menu items...
    private final JMenuItem update = new JMenuItem();
    private final JMenuItem release = new JMenuItem();
    private final JMenuItem clear = new JMenuItem();
    private final JMenuItem open = new JMenuItem();
    private final JMenuItem save = new JMenuItem();
    private final JMenuItem quit = new JMenuItem();
    private final JMenuItem copyTranslation = new JMenuItem();
    private final JMenuItem language = new JMenuItem();

    // buttons...
    private final JButton openButton = new JButton();
    private final JButton updateButton = new JButton();
    private final JButton releaseButton = new JButton();
    private final JButton clearButton = new JButton();

    // other GUI elements...
    private final ProjectTranslationPanel translationPanel = new ProjectTranslationPanel();

    public I18NLinguist() {
	super("I18NLinguist v0.1.1");

	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	addWindowListener(this);

	initializeVariables();
	initializeMenu();
	initializeDesktop();
	initializeToolBar();
	pack();
    }

    private void initializeVariables() {

    }

    private void initializeMenu() {
	JMenuBar menuBar = new JMenuBar();
	setJMenuBar(menuBar);

	JMenu fileMenu = new JMenu();
	translationUpdater.i18n("File", translator, fileMenu);
	JMenu toolsMenu = new JMenu();
	translationUpdater.i18n("Tools", translator, toolsMenu);
	JMenu optionsMenu = new JMenu();
	translationUpdater.i18n("Options", translator, optionsMenu);
	JMenu helpMenu = new JMenu();
	translationUpdater.i18n("Help", translator, helpMenu);

	menuBar.add(fileMenu);
	menuBar.add(toolsMenu);
	menuBar.add(optionsMenu);
	menuBar.add(helpMenu);

	fileMenu.add(open);
	translationUpdater.i18n("Open...", translator, open);
	keyStrokeUpdater.i18n("ctrl O", translator, open);
	open.addActionListener(this);

	fileMenu.add(save);
	translationUpdater.i18n("Save...", translator, save);
	keyStrokeUpdater.i18n("ctrl S", translator, save);
	save.addActionListener(this);

	fileMenu.addSeparator();

	fileMenu.add(quit);
	translationUpdater.i18n("Quit", translator, quit);
	keyStrokeUpdater.i18n("ctrl Q", translator, quit);
	quit.addActionListener(this);

	toolsMenu.add(update);
	translationUpdater.i18n("Update", translator, update);
	update.addActionListener(this);

	toolsMenu.add(release);
	translationUpdater.i18n("Release", translator, release);
	release.addActionListener(this);

	toolsMenu.add(clear);
	translationUpdater.i18n("Clear", translator, clear);
	clear.addActionListener(this);

	toolsMenu.add(copyTranslation);
	translationUpdater.i18n("Copy Translation...", translator, copyTranslation);
	copyTranslation.addActionListener(this);

	optionsMenu.add(language);
	translationUpdater.i18n("Language...", translator, language);
	language.addActionListener(this);
    }

    private void initializeDesktop() {
	setLayout(new BorderLayout());
	add(translationPanel);
    }

    private void initializeToolBar() {
	JToolBar tools = new JToolBar();
	tools.add(openButton);
	translationUpdater.i18n("Open...", translator, openButton);
	openButton.addActionListener(this);

	tools.add(updateButton);
	translationUpdater.i18n("Update", translator, updateButton);
	updateButton.addActionListener(this);

	tools.add(releaseButton);
	translationUpdater.i18n("Release", translator, releaseButton);
	releaseButton.addActionListener(this);

	tools.add(clearButton);
	translationUpdater.i18n("Clear", translator, clearButton);
	clearButton.addActionListener(this);

	add(tools, BorderLayout.NORTH);

    }

    private void update() {
	try {
	    I18NUpdate.update(translationPanel.getDirectory());
	} catch (IOException e) {
	    e.printStackTrace();
	    JOptionPane.showMessageDialog(this,
		    translator.i18n("IO error during update!\n\nMessage was:\n{0}", e.getMessage()),
		    translator.i18n("Error"), JOptionPane.ERROR_MESSAGE);
	}
    }

    private void release() {
	try {
	    I18NRelease.release(translationPanel.getDirectory());
	} catch (IOException e) {
	    e.printStackTrace();
	    JOptionPane.showMessageDialog(this,
		    translator.i18n("IO error during release!\n\nMessage was:\n{0}", e.getMessage()),
		    translator.i18n("Error"), JOptionPane.ERROR_MESSAGE);
	}
    }

    private void clear() {
	translationPanel.removeObsoletePhrases();
    }

    private void copyTranslation() {
	try {
	    TranslationCopyDialog dlg = new TranslationCopyDialog(this);
	    dlg.setVisible(true);
	    if (dlg.isFinishedByOK()) {
		System.out.println("Copy from " + dlg.getSource() + " to " + dlg.getTarget() + "...");
		Locale sourceLocale = dlg.getSource();
		Locale targetLocale = dlg.getTarget();
		I18NProjectConfiguration configuration;
		configuration = new I18NProjectConfiguration(translationPanel.getDirectory());
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
	    JOptionPane.showMessageDialog(this,
		    translator.i18n("Could not copy translations due to IO error!\n\nMessage: {0}", e.getMessage()),
		    translator.i18n("Error"), JOptionPane.ERROR_MESSAGE);
	}
    }

    private void open() {
	JFileChooser fileChooser = new JFileChooser();
	fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	int result = fileChooser.showOpenDialog(this);
	if (result == JFileChooser.CANCEL_OPTION) {
	    return;
	}
	if (result == JFileChooser.ERROR_OPTION) {
	    JOptionPane.showConfirmDialog(this, translator.i18n("Error"), translator.i18n("Error while choosing file."),
		    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
	    return;
	}
	translationPanel.openDirectory(fileChooser.getSelectedFile());
    }

    private void save() {
	try {
	    translationPanel.saveFile();
	} catch (IOException e) {
	    e.printStackTrace();
	    JOptionPane.showConfirmDialog(this, translator.i18n("I18NFile could not be saved!"),
		    translator.i18n("Error"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
	}
    }

    private void setLanguage() {
	new LanguageDialog(this).setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent o) {
	if ((o.getSource() == open) || (o.getSource() == openButton)) {
	    open();
	} else if (o.getSource() == save) {
	    save();
	} else if (o.getSource() == quit) {
	    dispose();
	} else if ((o.getSource() == update) || (o.getSource() == updateButton)) {
	    update();
	} else if ((o.getSource() == release) || (o.getSource() == releaseButton)) {
	    release();
	} else if ((o.getSource() == clear) || (o.getSource() == clearButton)) {
	    clear();
	} else if ((o.getSource() == copyTranslation)) {
	    copyTranslation();
	} else if (o.getSource() == language) {
	    setLanguage();
	}
    }

    @Override
    public void windowActivated(WindowEvent arg0) {
    }

    @Override
    public void windowClosed(WindowEvent arg0) {
    }

    @Override
    public void windowClosing(WindowEvent arg0) {
	dispose();
    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {
    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {
    }

    @Override
    public void windowIconified(WindowEvent arg0) {
    }

    @Override
    public void windowOpened(WindowEvent arg0) {
    }

    public static void main(String[] args) {
	I18NLinguist app = new I18NLinguist();
	app.setVisible(true);
    }
}
