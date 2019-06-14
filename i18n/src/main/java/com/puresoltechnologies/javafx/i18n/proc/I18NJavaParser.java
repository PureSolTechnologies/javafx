/****************************************************************************
 *
 *   I18NJavaParser.java
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

package com.puresoltechnologies.javafx.i18n.proc;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Pattern;

import com.puresoltechnologies.javafx.i18n.data.MultiLanguageTranslations;

/**
 * This object reads Java files and searches for I18N strings which can be
 * collected afterwards in an I18N file for translation.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class I18NJavaParser {

    /**
     * This method is used to parse a file for I18N Strings.
     * 
     * @param file to be read and processed.
     * @return A Vector of all I18N strings is returned.
     * @throws IOException is thrown in case of I/O issues.
     */
    static public MultiLanguageTranslations parseFile(File file) throws IOException {
	I18NJavaParser parser = new I18NJavaParser(file);
	return parser.parse();
    }

    private File file = null;
    private String packageName = "";

    static private final Pattern I18N_PATTERN = Pattern.compile(".*i18n\\s*\\(.*");
    static private final Pattern I18N_PATTERN_2 = Pattern.compile(".*\"i18n:.*");

    private I18NJavaParser(File file) {
	this.file = file;
    }

    /**
     * This is the internal method started for processing.
     * 
     * @param file is the file to be processed.
     * @return A Vector of all I18N strings is returned.
     * @throws IOException
     */
    private MultiLanguageTranslations parse() throws IOException {
	RandomAccessFile f = new RandomAccessFile(file, "r");
	return readFileAndPrepareData(f);
    }

    /**
     * This method reads a whole file, trims the lines and adds everything to one
     * String object.
     * 
     * @param file is the file to read.
     * @return A String is returned containing the content of the file reduced by
     *         its end of lines.
     * @throws IOException
     */
    private MultiLanguageTranslations readFileAndPrepareData(RandomAccessFile f) throws IOException {
	MultiLanguageTranslations translations = new MultiLanguageTranslations();
	String line;
	StringBuffer buffer = null;
	int lineNumber = 0;
	while ((line = readLineWithoutComments(f)) != null) {
	    buffer = new StringBuffer(line);
	    lineNumber++;
	    int startLineNumber = lineNumber;
	    while (buffer.toString().endsWith("\"") || buffer.toString().endsWith("+")
		    || buffer.toString().endsWith(",")) {
		String nextLine = readLineWithoutComments(f);
		lineNumber++;
		if (nextLine == null) {
		    throw new IOException("Unexpected end of file!");
		}
		buffer.append(nextLine);
	    }
	    // remove string appends: '" + "' --> ""
	    line = buffer.toString().replaceAll("\"\\s*\\+\\s*\"", "");
	    if (line.contains("package")) {
		packageName = extractPackageName(line);
		continue;
	    }
	    if (line.isEmpty()) {
		continue;
	    }
	    translations.add(extractI18N(line, startLineNumber, lineNumber));
	}
	return translations;
    }

    private String readLineWithoutComments(RandomAccessFile f) throws IOException {
	String line;
	line = f.readLine();
	if (line != null) {
	    // remove trailing comments '//...' with various spaces
	    line = line.replaceAll("\\/\\/.*$", "");
	    line = line.trim();
	}
	return line;
    }

    private String extractPackageName(String line) {
	String name = line.replaceAll("^.*package\\s*", "");
	return name.replaceAll("\\s*;\\s*$", "");
    }

    private MultiLanguageTranslations extractI18N(String line, int startLine, int endLine) {
	MultiLanguageTranslations translations = new MultiLanguageTranslations();
	if ((!I18N_PATTERN.matcher(line).matches()) && (!I18N_PATTERN_2.matcher(line).matches())) {
	    return translations;
	}
	// remove all context parameters...
	line = line.replaceAll("i18n\\s*\\(\\s*[^\",]+\\s*,\\s*\"", "i18n(\"");
	// find "i18n:..." constructions and convert them to i18n("...")
	line = line.replaceAll("\"i18n:", "i18n(\"");
	// split at 'i18n("'
	String[] i18ns = line.split("i18n\\s*\\(\\s*\"");
	for (int index = 1; index < i18ns.length; index++) {
	    String source = extractStringFromStartToEnd("\"" + i18ns[index]);
	    if ((source != null) && (!source.isEmpty())) {
		translations.add(MultiLanguageTranslations.create(source.replaceAll("\\\\n", "\n"),
			packageName + "." + file.getName(), startLine));
	    }
	}
	return translations;
    }

    /**
     * This method takes a string which directly starts with an I18N string and
     * looks for the right quotation mark as end of the I18N string. The first
     * quotation with an even number of backslashes before it is the right end of
     * I18N string.
     * 
     * @param line to be processed.
     * @return The correct I18N String is returned.
     */
    private String extractStringFromStartToEnd(String line) {
	if (!line.startsWith("\"")) {
	    // remove context parameter if present
	    line = line.replaceAll("^[^,\\)]*,\\s*", "");
	    if (!line.startsWith("\"")) {
		// not a String constant found with starting "
		// maybe a variable is used here, nothing to translate...
		return null;
	    }
	}
	line = line.replaceAll("^\"", "");
	int bsCount = 0; // backslash counter
	for (int index = 0; index < line.length(); index++) {
	    if (line.charAt(index) == '\"') {
		if ((bsCount % 2) == 0) {
		    return line.substring(0, index);
		}
	    }
	    if (line.charAt(index) == '\\') {
		bsCount++;
	    } else {
		bsCount = 0;
	    }
	}
	return null;
    }
}
