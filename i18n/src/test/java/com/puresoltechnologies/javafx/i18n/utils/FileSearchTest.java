/****************************************************************************
 *
 *   FileSearchTest.java
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

package com.puresoltechnologies.javafx.i18n.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class FileSearchTest {

    @Test
    public void testOSSpecificSeparator() {
	String osName = System.getProperty("os.name");
	assertNotNull(osName);
	assertFalse(osName.isEmpty());
	if (osName.equals("Windows XP")) {
	    assertEquals("\\", File.separator);
	} else if (osName.equals("Linux")) {
	    assertEquals("/", File.separator);
	} else {
	    throw new RuntimeException("Operating system '" + osName + "'not included in test! Add needed.");
	}
    }

    @Test
    public void testPatternBehaviour() {
	assertFalse(Pattern.matches("[^\\\\]", "\\"));
	assertTrue(Pattern.matches("[^\\\\]", "/"));
    }

    @Test
    public void testPatternConversion() {
	// basic elements
	assertEquals("\\.", FileSearch.wildcardsToRegExp("."));
	if (File.separator.equals("\\")) {
	    assertEquals("[^\\\\]", FileSearch.wildcardsToRegExp("?"));
	    assertEquals("[^\\\\]*", FileSearch.wildcardsToRegExp("*"));
	} else {
	    assertEquals("[^/]", FileSearch.wildcardsToRegExp("?"));
	    assertEquals("[^/]*", FileSearch.wildcardsToRegExp("*"));
	}
    }

    @Test
    public void testRecursiveFileSearch() {
	List<File> files = FileSearch.find(new File("."), "*.java");
	assertNotNull(files);
	assertTrue(files.size() > 0);
	boolean foundSelf = false;
	boolean foundClass = false;
	for (File file : files) {
	    System.out.println(file);
	    if (file.equals(new File("/src/test/java/javax/i18n4java/utils/FileSearchTest.java"))) {
		foundSelf = true;
	    }
	    if (file.equals(new File("/src/main/java/javax/i18n4java/utils/FileSearch.java"))) {
		foundClass = true;
	    }
	}
	assertTrue(foundSelf);
	assertTrue(foundClass);
    }
}
