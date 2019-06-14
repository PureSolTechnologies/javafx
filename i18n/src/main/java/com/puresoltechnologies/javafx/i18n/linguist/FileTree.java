/****************************************************************************
 *
 *   FileTree.java
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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class FileTree implements Comparable<FileTree> {

    /**
     * This method is static to express the functional character of the method and
     * to avoid accidental usage of non static fields.
     * 
     * @param currentNode
     * @param file
     * @return
     */
    private static FileTree getFileTreeElement(FileTree currentNode, File file) {
	if (currentNode.getFile().equals(file)) {
	    return currentNode;
	}
	for (int index = 0; index < currentNode.getChildCount(); index++) {
	    FileTree child = currentNode.getChild(index);
	    if (file.getPath().startsWith(child.getFile().getPath())) {
		return getFileTreeElement(child, file);
	    }
	}
	return null;
    }

    /*
     * ********************************************************************** Non
     * static part...
     * **********************************************************************
     */

    private final List<FileTree> children = new ArrayList<>();
    private final FileTree parent;
    private final String name;
    private Status status = Status.EMPTY;

    public FileTree(String file) {
	super();
	parent = null;
	this.name = file;
    }

    public FileTree(FileTree parent, String file) {
	super();
	this.parent = parent;
	this.name = file;
	parent.addChild(this);
    }

    private void addChild(FileTree child) {
	children.add(child);
	Collections.sort(children);
    }

    public FileTree getParent() {
	return parent;
    }

    public String getName() {
	return name;
    }

    public File getFile() {
	FileTree node = this;
	File path = new File("");
	do {
	    path = new File(node.getName(), path.getPath());
	    node = node.getParent();
	} while (node != null);
	return path;
    }

    public FileTree getChild(int id) {
	return children.get(id);
    }

    public int getChildCount() {
	return children.size();
    }

    public int getIndexOfChild(FileTree child) {
	return children.indexOf(child);
    }

    public boolean hashChildren() {
	return children.size() > 0;
    }

    public Status getStatus() {
	return status;
    }

    public void setStatus(Status status) {
	this.status = status;
    }

    @Override
    public String toString() {
	return name;
    }

    public boolean containsChild(String part) {
	for (FileTree child : children) {
	    if (child.getName().equals(part)) {
		return true;
	    }
	}
	return false;
    }

    public FileTree getChild(String part) {
	for (FileTree child : children) {
	    if (child.getName().equals(part)) {
		return child;
	    }
	}
	return null;
    }

    public FileTree getFileTreeElement(File file) {
	return getFileTreeElement(this, file);
    }

    @Override
    public int compareTo(FileTree other) {
	if ((!this.hashChildren()) && (other.hashChildren())) {
	    return 1;
	}
	if ((this.hashChildren()) && (!other.hashChildren())) {
	    return -1;
	}
	return this.name.compareTo(other.name);
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = (prime * result) + ((name == null) ? 0 : name.hashCode());
	result = (prime * result) + ((parent == null) ? 0 : parent.hashCode());
	result = (prime * result) + ((status == null) ? 0 : status.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	FileTree other = (FileTree) obj;
	if (name == null) {
	    if (other.name != null) {
		return false;
	    }
	} else if (!name.equals(other.name)) {
	    return false;
	}
	if (parent == null) {
	    if (other.parent != null) {
		return false;
	    }
	} else if (!parent.equals(other.parent)) {
	    return false;
	}
	if (status != other.status) {
	    return false;
	}
	return true;
    }

}
