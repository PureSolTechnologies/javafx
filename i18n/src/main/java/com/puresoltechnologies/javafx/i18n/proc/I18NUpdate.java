/****************************************************************************
 *
 *   I18NUpdate.java
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
import java.util.ArrayList;
import java.util.List;

import com.puresoltechnologies.javafx.i18n.data.I18NFile;
import com.puresoltechnologies.javafx.i18n.data.MultiLanguageTranslations;
import com.puresoltechnologies.javafx.i18n.proc.I18NJavaParser;
import com.puresoltechnologies.javafx.i18n.proc.I18NProjectConfiguration;
import com.puresoltechnologies.javafx.i18n.utils.FileSearch;

/**
 * This applications reads files and directories given by command line and
 * search the found files for strings to be translated. This tools is similar to
 * Trolltech's lupdate.
 * 
 * @author Rick-Rainer Ludwig
 */
public class I18NUpdate {

	public static void update(File projectDirectory) throws IOException {
		new I18NUpdate(projectDirectory).update();
	}

	private final I18NProjectConfiguration configuration;
	private final List<File> inputFiles = new ArrayList<File>();

	private I18NUpdate(File projectDirectory) throws IOException {
		configuration = new I18NProjectConfiguration(projectDirectory);
	}

	private void update() throws IOException {
		findAllInputFiles();
		processFiles();
	}

	private void findAllInputFiles() {
		inputFiles.addAll(FileSearch.find(configuration.getSourceDirectory(),
				"*.java"));
	}

	private void processFiles() throws IOException {
		for (File file : inputFiles) {
			processFile(file);
		}
	}

	private void processFile(File file) throws IOException {
		File sourceFile = new File(configuration.getSourceDirectory(),
				file.getPath());
		MultiLanguageTranslations i18nSources = collectI18NSource(sourceFile);
		if (i18nSources.hasSources()) {
			File i18nFile = new File(configuration.getI18nDirectory(), I18NFile
					.getResource(sourceFile).getPath());
			addNewSourcesToExistingFile(i18nFile, i18nSources);
		}
	}

	private MultiLanguageTranslations collectI18NSource(File file)
			throws IOException {
		return I18NJavaParser.parseFile(file);
	}

	private void addNewSourcesToExistingFile(File file,
			MultiLanguageTranslations i18nSources) throws IOException {
		MultiLanguageTranslations translations = readTranslations(file);
		translations.add(i18nSources);
		File directory = new File(file.getPath().replaceAll(file.getName(), ""));
		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				throw new IOException("Could not create directory '"
						+ directory + "'!");
			}
		}
		writeTranslations(file, translations);
	}

	private MultiLanguageTranslations readTranslations(File file) {
		try {
			MultiLanguageTranslations hash = I18NFile.read(file);
			hash.removeLocations();
			return hash;
		} catch (IOException e) {
			return new MultiLanguageTranslations();
		}
	}

	private void writeTranslations(File file,
			MultiLanguageTranslations translations) throws IOException {
		I18NFile.write(file, translations);
	}
}
