/****************************************************************************
 *
 *   FileTreeTest.java
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.rcp.linguist.FileTree;

public class FileTreeTest {

    @Test
    public void testInstance() {
	assertNotNull(new FileTree("/"));
	assertNotNull(new FileTree(new FileTree("/"), "/test"));
    }

    @Test
    public void testDefaultValue() {
	FileTree fileTree = new FileTree("/");
	assertEquals(null, fileTree.getParent());
	assertEquals("/", fileTree.getName());

	assertEquals(0, fileTree.getChildCount());
	assertFalse(fileTree.hashChildren());

	FileTree root = new FileTree("/");
	fileTree = new FileTree(root, "/test");
	assertSame(root, fileTree.getParent());
	assertEquals("/test", fileTree.getName());
    }

}
