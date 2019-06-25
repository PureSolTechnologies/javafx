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

package com.puresoltechnologies.javafx.rcp.linguist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.puresoltechnologies.javafx.i18n.Translator;
import com.puresoltechnologies.javafx.i18n.data.I18NFile;
import com.puresoltechnologies.javafx.i18n.proc.I18NProjectConfiguration;
import com.puresoltechnologies.javafx.i18n.utils.FileSearch;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

class FilesTranslationPanel extends BorderPane {

    private static final long serialVersionUID = 1L;

    private static final Translator translator = Translator.getTranslator(FilesTranslationPanel.class);

    // GUI elements...
    private final TreeView<FileTree> fileTree = new TreeView<>();
    private final TranslationPanel translationPanel = new TranslationPanel();

    private I18NProjectConfiguration configuration = null;
    private final File i18nFile = null;
    private final FileTree fileTreeNode = null;

    public FilesTranslationPanel() {
	super();
	initializeDesktop();
    }

    private void initializeDesktop() {
//	fileTree.setCellFactory((view) -> new TreeCell<>() {
//	    @Override
//	    protected void updateItem(FileTree file, boolean empty) {
//		super.updateItem(file, empty);
//		if (empty) {
//		    setText("");
//		    setGraphic(null);
//		} else {
//		    new StatusComponent(file.getName(), isSelected(), isFocused(), file.getStatus());
//		}
//	    }
//	});
	ObjectProperty<MultipleSelectionModel<TreeItem<FileTree>>> selectionModelProperty = fileTree
		.selectionModelProperty();
	selectionModelProperty.get().setSelectionMode(SelectionMode.SINGLE);
//	selectionModelProperty.get().select
	selectionModelProperty.addListener((oldValue, newValue, component) -> {
	});

	setCenter(new SplitPane(new ScrollPane(fileTree), translationPanel));

	setBottom(new Label("Some project statistics"));
    }

    /**
     * @param selectedLocale the selectedLocale to set
     * @throws IOException
     */
    public void setSelectedLocale(Locale selectedLocale) throws IOException {
	translationPanel.setSelectedLocale(selectedLocale);
//	fileTreeNode.setSelectedLocale(selectedLocale);
    }

    public boolean hasChanged() {
	return translationPanel.hasChanged();
    }

    /**
     * This method checks whether the content of the file was changed or not. If a
     * change was performed, the user is asked for saving the file or not. If the
     * used chooses cancel the file is not saved and this method returns false to
     * signal to abort the current process.
     *
     * @return True is returned if the process was satisfied and the calling method
     *         can proceed normally. False is returned in case the used chose cancel
     *         to abort the current process.
     * @throws IOException
     */
    public boolean saveIfChanged() throws IOException {
	if (!hasChanged()) {
	    return true;
	}
//	int result = JOptionPane.showConfirmDialog(this, translator.i18n("Changes were made.\nDo you want to save?"),
//		translator.i18n("Save"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
//	if (result == JOptionPane.CANCEL_OPTION) {
//	    return false;
//	}
//	if (result == JOptionPane.NO_OPTION) {
//	    return true;
//	}
	saveFile();
	return true;
    }

    public void saveFile() throws IOException {
	if (i18nFile != null) {
	    I18NFile.write(i18nFile, translationPanel.getTranslations());
	    translationPanel.setChanged(false);
//	    fileTreeModel.changedFile(i18nFile);
	}
    }

    public void openDirectory(File directory) {
	try {
	    if (!saveIfChanged()) {
		return;
	    }
	    configuration = new I18NProjectConfiguration(directory);

	    List<File> files = FileSearch.find(configuration.getI18nDirectory(), "*.i18n");
//	    fileTreeNode = setFiles(files);
	    FileTree.setStatusFlags(fileTreeNode, Locale.getDefault());
	} catch (IOException e) {
//	    JOptionPane.showConfirmDialog(this, translator.i18n("Error"), translator.i18n("IO error in file reading."),
//		    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
	}
    }

    private FileTree setFiles(List<File> files) {
	FileTree fileTree = new FileTree(configuration.getI18nDirectory().getPath() + "/");
	for (File file : files) {
	    List<String> parts = new ArrayList<>();
	    File currentFile = file;
	    do {
		parts.add(0, currentFile.getName());
		currentFile = currentFile.getParentFile();
	    } while (currentFile != null);
	    FileTree currentNode = fileTree;
	    for (String part : parts) {
		if (part.isEmpty()) {
		    continue;
		}
		if (currentNode.containsChild(part)) {
		    currentNode = currentNode.getChild(part);
		} else {
		    currentNode = new FileTree(currentNode, part);
		}
	    }
	}
	return fileTree;
    }

    public void openFile(File file) {
	try {
	    if (!saveIfChanged()) {
		return;
	    }
//	    i18nFile = file;
	    translationPanel.setTranslations(I18NFile.read(i18nFile));
	} catch (IOException e) {
//	    JOptionPane.showMessageDialog(this, translator.i18n("The file {0} could not be read!", i18nFile),
//		    translator.i18n("File not found"), JOptionPane.ERROR_MESSAGE);
//	    i18nFile = null;
	}
    }

    public File getDirectory() {
	return configuration.getProjectDirectory();
    }

    public void removeObsoletePhrases() {
	translationPanel.removeObsoletePhrases();
    }

//    @Override
//    public void valueChanged(TreeSelectionEvent o) {
//	if (o.getSource() == fileTree) {
//	    FileTree fileTree = (FileTree) o.getPath().getLastPathComponent();
//	    if (!fileTree.hashChildren()) {
//		File file = fileTree.getFile();
//		if (!file.equals(i18nFile)) {
//		    openFile(file);
//		}
//	    }
//	}
//    }
}