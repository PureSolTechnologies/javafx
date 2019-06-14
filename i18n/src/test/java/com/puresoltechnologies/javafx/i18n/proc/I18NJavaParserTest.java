/****************************************************************************
 *
 *   I18NJavaParserTest.java
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.i18n.Translator;
import com.puresoltechnologies.javafx.i18n.data.MultiLanguageTranslations;

/**
 * This test case checks the I18N Java parser for functionality.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class I18NJavaParserTest {

    private static final Translator translator = Translator.getTranslator(I18NJavaParserTest.class);

    /**
     * This method just includes different representations of I18N strings.
     */
    public void translationExamples() {
	System.out.println(translator.i18n("tr(String)"));
	System.out.println(translator.i18n("tr(String, Objects)", 123, "abc"));
	System.out.println(Translator.getTranslator(I18NJavaParserTest.class).i18n("tr(Context,String)"));
	System.out.println(
		Translator.getTranslator(I18NJavaParserTest.class).i18n("tr(Context,String, Objects)", 123, "abc"));
	System.out.println(translator.i18n("This test for processing multi line" + "texts in tr()!"));

	System.out.println(translator.i18n("Test!"));
    }

    @Test
    public void testParser() throws Exception {
	MultiLanguageTranslations translations = I18NJavaParser
		.parseFile(new File("src/test/java/javax/i18n4java/proc/I18NJavaParserTest.java"));
	assertNotNull(translations);
	assertNotNull(translations.get("tr(String)"));
	assertNotNull(translations.get("tr(String)"));
	assertNotNull(translations.get("tr(String, Objects)"));
	assertNotNull(translations.get("tr(Context,String)"));
	assertNotNull(translations.get("tr(Context,String, Objects)"));
	assertNotNull(translations.get("This test for processing multi line" + "texts in tr()!"));
    }

    @Test
    public void testFileNotFound() throws Exception {
	assertThrows(FileNotFoundException.class, () -> {
	    I18NJavaParser.parseFile(new File("this/file/is/not/existent"));
	});
    }
}
