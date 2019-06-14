/****************************************************************************
 *
 *   FileSearch.java
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class was implemented for recursive file search.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class FileSearch {

	public static String wildcardsToRegExp(String pattern) {
		pattern = pattern.replaceAll("\\.", "\\\\.");
		if (File.separator.equals("\\")) {
			pattern = pattern.replaceAll("\\*", "[^\\\\\\\\]*");
			pattern = pattern.replaceAll("\\?", "[^\\\\\\\\]");
		} else {
			pattern = pattern.replaceAll("\\*", "[^" + File.separator + "]*");
			pattern = pattern.replaceAll("\\?", "[^" + File.separator + "]");
		}
		return pattern;
	}

	public static List<File> find(File directory, String pattern) {
		pattern = wildcardsToRegExp(pattern);
		List<File> files = findFilesInDirectory(directory,
				Pattern.compile(pattern), true);
		List<File> result = new ArrayList<File>();
		for (File file : files) {
			String fileString = file.getPath().substring(
					directory.getPath().length());
			result.add(new File(fileString));
		}
		return result;
	}

	/**
	 * This class is the recursive part of the file search.
	 * 
	 * @param directory
	 * @param pattern
	 * @param scanRecursive
	 * @return
	 */
	private static List<File> findFilesInDirectory(File directory,
			Pattern pattern, boolean scanRecursive) {
		List<File> files = new ArrayList<File>();
		String[] filesInDirectory = directory.list();
		if (filesInDirectory == null) {
			return files;
		}
		for (String fileToCheck : filesInDirectory) {
			File file = new File(directory, fileToCheck);
			if (file.isFile()) {
				if (pattern.matcher(fileToCheck).matches()) {
					files.add(file);
				}
			} else if (file.isDirectory()) {
				if (scanRecursive) {
					files.addAll(findFilesInDirectory(file, pattern,
							scanRecursive));
				}
			}
		}
		return files;
	}
}
