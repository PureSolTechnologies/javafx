/****************************************************************************
 *
 *   FileTreeModel.java
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.puresoltechnologies.javafx.i18n.data.I18NFile;
import com.puresoltechnologies.javafx.i18n.data.MultiLanguageTranslations;
import com.puresoltechnologies.javafx.i18n.proc.I18NProjectConfiguration;

class FileTreeModel implements TreeModel {

	/**
	 * This method is static to express the functional character of the method
	 * and to avoid accidental usage of non static fields.
	 * 
	 * @param file
	 * @param locale
	 * @return
	 * @throws IOException
	 */
	private static Status getStatus(File file, Locale locale)
			throws IOException {
		if (file.exists() && file.isFile()) {
			MultiLanguageTranslations translations = I18NFile.read(file);
			if (!translations.getAvailableLanguages().contains(locale)) {
				return Status.EMPTY;
			}
			if (translations.isTranslationFinished(locale)) {
				return Status.FINISHED;
			}
			return Status.ONGOING;
		}
		return Status.EMPTY;
	}

	/**
	 * This method is static to express the functional character of the method
	 * and to avoid accidental usage of non static fields.
	 * 
	 * @param currentNode
	 * @throws IOException
	 */
	private static void setStatusFlags(FileTree currentNode, Locale locale)
			throws IOException {
		if (!currentNode.hashChildren()) {
			currentNode.setStatus(getStatus(currentNode.getFile(), locale));
			return;
		}
		Status status = Status.EMPTY;
		boolean first = true;
		for (int index = 0; index < currentNode.getChildCount(); index++) {
			FileTree child = currentNode.getChild(index);
			setStatusFlags(child, locale);
			if (first) {
				first = false;
				status = child.getStatus();
			} else {
				switch (child.getStatus()) {
				case EMPTY:
					if (status == Status.FINISHED) {
						status = Status.ONGOING;
					}
					break;
				case ONGOING:
					status = Status.ONGOING;
					break;
				case FINISHED:
					if (status == Status.EMPTY) {
						status = Status.ONGOING;
					}
				}
			}
		}
		currentNode.setStatus(status);
	}

	/*
	 * **********************************************************************
	 * Non static part
	 * **********************************************************************
	 */

	private final List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();
	private Locale selectedLocale = Locale.getDefault();
	private FileTree fileTree = null;
	private I18NProjectConfiguration configuration = null;

	public FileTreeModel() {
		super();
	}

	public FileTreeModel(List<File> files,
			I18NProjectConfiguration configuration) throws IOException {
		super();
		this.configuration = configuration;
		setFiles(files, configuration);
	}

	public Locale getSelectedLocale() {
		return selectedLocale;
	}

	public void setSelectedLocale(Locale selectedLocale) throws IOException {
		this.selectedLocale = selectedLocale;
		setStatusFlags();
		fireStructureChanged();
	}

	public I18NProjectConfiguration getConfiguration() {
		return configuration;
	}

	public void setFiles(List<File> files,
			I18NProjectConfiguration configuration) throws IOException {
		this.configuration = configuration;
		setFileTree(files);
		setStatusFlags();
		fireStructureChanged();
	}

	private void setFileTree(List<File> files) {
		fileTree = new FileTree(configuration.getI18nDirectory().getPath()
				+ "/");
		for (File file : files) {
			List<String> parts = new ArrayList<String>();
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
	}

	private void setStatusFlags() throws IOException {
		if ((fileTree != null) && (selectedLocale != null)) {
			setStatusFlags(fileTree, selectedLocale);
		}
	}

	private void fireStructureChanged() {
		if (fileTree != null) {
			for (TreeModelListener listener : listeners) {
				listener.treeStructureChanged(new TreeModelEvent(this,
						new TreePath(fileTree)));
			}
		}
	}

	private void fireFileChanged(File file) {
		for (TreeModelListener listener : listeners) {
			listener.treeNodesChanged(new TreeModelEvent(this, new TreePath(
					fileTree.getFileTreeElement(file))));
		}
	}

	public void changedFile(File file) throws IOException {
		FileTree fileTreeElement = fileTree.getFileTreeElement(file);
		fileTreeElement.setStatus(getStatus(file, selectedLocale));
		setStatusFlags();
		fireFileChanged(file);
	}

	@Override
	public void addTreeModelListener(TreeModelListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener listener) {
		listeners.remove(listener);
	}

	@Override
	public Object getChild(Object parent, int id) {
		return ((FileTree) parent).getChild(id);
	}

	@Override
	public int getChildCount(Object parent) {
		return ((FileTree) parent).getChildCount();
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return ((FileTree) parent).getIndexOfChild((FileTree) child);
	}

	@Override
	public Object getRoot() {
		return fileTree;
	}

	@Override
	public boolean isLeaf(Object child) {
		return !((FileTree) child).hashChildren();
	}

	@Override
	public void valueForPathChanged(TreePath arg0, Object arg1) {
		throw new RuntimeException("Not implemented and supported!");
	}

	/**
	 * This method is for testing purposes only.
	 * 
	 * @return
	 */
	Object getFileTree() {
		return fileTree;
	}

}
