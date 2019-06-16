/****************************************************************************
 *
 *   FileTreeModelTest.java
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.i18n.proc.I18NProjectConfiguration;

public class FileTreeModelTest {

    private final static List<File> FILES = new ArrayList<>();
    static {
	FILES.add(new File("/dir1/file11"));
	FILES.add(new File("/dir1/file12"));
	FILES.add(new File("/dir1/file13"));
	FILES.add(new File("/dir1/dir11/file111"));
	FILES.add(new File("/dir1/dir11/file112"));
	FILES.add(new File("/dir1/dir11/file113"));
	FILES.add(new File("/dir1/dir12/file121"));
	FILES.add(new File("/dir1/dir12/file122"));
	FILES.add(new File("/dir1/dir13/file131"));
	FILES.add(new File("/dir2/file21"));
	FILES.add(new File("/dir2/file22"));
	FILES.add(new File("/dir2/file23"));
	FILES.add(new File("/dir3/file31"));
	FILES.add(new File("/dir3/file32"));
	FILES.add(new File("/dir3/file33"));
    }

    @Test
    public void testAssumptions() {
	File file = new File("");
	assertEquals("", file.getPath());
	assertEquals(null, file.getParent());

	file = new File("src");
	assertEquals("src", file.getPath());
	assertEquals(null, file.getParent());

	file = new File("/");
	assertEquals(File.separator, file.getPath());
	assertEquals(null, file.getParent());

	file = new File("/src");
	assertEquals(File.separator + "src", file.getPath());
	assertEquals(File.separator, file.getParent());
    }

    @Test
    public void testInstance() throws Exception {
	assertNotNull(new FileTreeModel());
	assertNotNull(new FileTreeModel(FILES, new I18NProjectConfiguration(new File("."))));
    }

    @Test
    public void testGetChildCount() throws Exception {
	FileTreeModel model = new FileTreeModel(FILES, new I18NProjectConfiguration(new File(".")));
	assertEquals(3, model.getChildCount(model.getFileTree()));
    }

}
