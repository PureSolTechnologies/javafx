/****************************************************************************
 *
 *   FilesTranslationPanel.java
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

package com.puresoltechnologies.javafx.i18n.linguist;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.puresoltechnologies.javafx.i18n.TranslationUpdater;
import com.puresoltechnologies.javafx.i18n.Translator;
import com.puresoltechnologies.javafx.i18n.data.I18NFile;
import com.puresoltechnologies.javafx.i18n.proc.I18NProjectConfiguration;
import com.puresoltechnologies.javafx.i18n.utils.FileSearch;

class FilesTranslationPanel extends JPanel implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;

	private static final Translator translator = Translator
			.getTranslator(FilesTranslationPanel.class);

	private final TranslationUpdater translationUpdater = new TranslationUpdater();

	// GUI elements...
	private final FileTreeModel fileTreeModel = new FileTreeModel();
	private final JTree fileTree = new JTree(fileTreeModel);
	private final TranslationPanel translationPanel = new TranslationPanel();

	private I18NProjectConfiguration configuration = null;
	private File i18nFile = null;

	public FilesTranslationPanel() {
		super();
		initializeDesktop();
	}

	private void initializeDesktop() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(5);
		borderLayout.setVgap(5);
		setLayout(borderLayout);

		fileTree.setCellRenderer(new FileTreeCellRenderer());
		fileTree.setBorder(translationUpdater.i18n("Classes", translator,
				fileTree, BorderFactory.createTitledBorder("")));
		fileTree.addTreeSelectionListener(this);

		add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(
				fileTree), translationPanel));

		add(new JLabel("Some project statistics"), BorderLayout.SOUTH);
	}

	/**
	 * @param selectedLocale
	 *            the selectedLocale to set
	 * @throws IOException
	 */
	public void setSelectedLocale(Locale selectedLocale) throws IOException {
		translationPanel.setSelectedLocale(selectedLocale);
		fileTreeModel.setSelectedLocale(selectedLocale);
		fileTree.repaint();
	}

	public boolean hasChanged() {
		return translationPanel.hasChanged();
	}

	/**
	 * This method checks whether the content of the file was changed or not. If
	 * a change was performed, the user is asked for saving the file or not. If
	 * the used chooses cancel the file is not saved and this method returns
	 * false to signal to abort the current process.
	 * 
	 * @return True is returned if the process was satisfied and the calling
	 *         method can proceed normally. False is returned in case the used
	 *         chose cancel to abort the current process.
	 * @throws IOException
	 */
	public boolean saveIfChanged() throws IOException {
		if (!hasChanged()) {
			return true;
		}
		int result = JOptionPane.showConfirmDialog(this,
				translator.i18n("Changes were made.\nDo you want to save?"),
				translator.i18n("Save"), JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.CANCEL_OPTION) {
			return false;
		}
		if (result == JOptionPane.NO_OPTION) {
			return true;
		}
		saveFile();
		return true;
	}

	public void saveFile() throws IOException {
		if (i18nFile != null) {
			I18NFile.write(i18nFile, translationPanel.getTranslations());
			translationPanel.setChanged(false);
			fileTreeModel.changedFile(i18nFile);
		}
	}

	public void openDirectory(File directory) {
		try {
			if (!saveIfChanged()) {
				return;
			}
			configuration = new I18NProjectConfiguration(directory);

			List<File> files = FileSearch.find(
					configuration.getI18nDirectory(), "*.i18n");
			fileTreeModel.setFiles(files, configuration);
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(this, translator.i18n("Error"),
					translator.i18n("IO error in file reading."),
					JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
		}
	}

	public void openFile(File file) {
		try {
			if (!saveIfChanged()) {
				return;
			}
			i18nFile = file;
			translationPanel.setTranslations(I18NFile.read(i18nFile));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, translator.i18n(
					"The file {0} could not be read!", i18nFile), translator
					.i18n("File not found"), JOptionPane.ERROR_MESSAGE);
			i18nFile = null;
		}
	}

	public File getDirectory() {
		return configuration.getProjectDirectory();
	}

	public void removeObsoletePhrases() {
		translationPanel.removeObsoletePhrases();
	}

	@Override
	public void valueChanged(TreeSelectionEvent o) {
		if (o.getSource() == fileTree) {
			FileTree fileTree = (FileTree) o.getPath().getLastPathComponent();
			if (!fileTree.hashChildren()) {
				File file = fileTree.getFile();
				if (!file.equals(i18nFile)) {
					openFile(file);
				}
			}
		}
	}
}
