/****************************************************************************
 *
 *   I18NRelease.java
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
import java.util.Locale;
import java.util.Set;

import com.puresoltechnologies.javafx.i18n.data.I18NFile;
import com.puresoltechnologies.javafx.i18n.data.LanguageSet;
import com.puresoltechnologies.javafx.i18n.data.MultiLanguageTranslations;
import com.puresoltechnologies.javafx.i18n.data.SingleLanguageTranslations;
import com.puresoltechnologies.javafx.i18n.data.TRFile;
import com.puresoltechnologies.javafx.i18n.proc.I18NProjectConfiguration;
import com.puresoltechnologies.javafx.i18n.utils.FileSearch;

/**
 * This application converts all i18n files in the the specified directory and
 * converts them into tr files for usage in internationalized applications.
 * 
 * @author Rick-Rainer Ludwig
 */
public class I18NRelease {

	public static void release(File projectDirectory) throws IOException {
		new I18NRelease(projectDirectory).release();
	}

	private final I18NProjectConfiguration configuration;
	private final List<File> inputFiles = new ArrayList<File>();

	private I18NRelease(File projectDirectory) throws IOException {
		configuration = new I18NProjectConfiguration(projectDirectory);
	}

	private void release() throws IOException {
		findAllInputFiles();
		processFiles();
	}

	private void findAllInputFiles() {
		inputFiles.addAll(FileSearch.find(configuration.getI18nDirectory(),
				"*.i18n"));
	}

	private void processFiles() throws IOException {
		for (File file : inputFiles) {
			processFile(file);
		}
	}

	private void processFile(File file) throws IOException {
		File sourceFile = new File(configuration.getI18nDirectory(),
				file.getPath());
		MultiLanguageTranslations mlTranslations = I18NFile.read(sourceFile);
		for (Locale language : mlTranslations.getAvailableLanguages()) {
			File destinationFile = new File(
					configuration.getDestinationDirectory(), file.getPath()
							.replaceAll("\\.i18n", "." + language + ".tr"));
			release(mlTranslations, language, destinationFile);
		}
	}

	private void release(MultiLanguageTranslations mlTranslations,
			Locale language, File file) throws IOException {
		SingleLanguageTranslations translations = new SingleLanguageTranslations();
		Set<String> sources = mlTranslations.getSources();
		for (String source : sources) {
			LanguageSet set = mlTranslations.get(source);
			if (set.has(language)) {
				translations.add(source, set.get(language));
			}
		}
		TRFile.write(file, translations);
	}
}
