/****************************************************************************
 *
 *   TranslatorTest.java
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

package com.puresoltechnologies.javafx.i18n;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.Test;

public class TranslatorTest {

    private static final Translator translator = Translator.getTranslator(TranslatorTest.class);

    @Test
    public void testSingleton() {
	assertNotNull(translator);
	assertSame(translator, Translator.getTranslator(TranslatorTest.class));
    }

    @Test
    public void testListeners() {
	LanguageChangeListener listener = translator -> System.out.println("translation has changed.");
	assertEquals(0, translator.getLanguageChangeListeners().size());
	translator.addLanguageChangeListener(listener);
	assertEquals(1, translator.getLanguageChangeListeners().size());
	translator.removeLanguageChangeListener(listener);
	assertEquals(0, translator.getLanguageChangeListeners().size());
	translator.addLanguageChangeListener(listener);
	assertEquals(1, translator.getLanguageChangeListeners().size());
	listener = null;
	System.gc();
	assertEquals(0, translator.getLanguageChangeListeners().size());
    }

    @Test
    public void testUnknownTranslation() {
	assertNotNull(translator);
	String origin = "This text was never translated!";
	String translation = translator.i18n(origin);
	assertEquals(origin, translation);
    }

    @Test
    public void testTranslation() {
	Translator.setDefault(new Locale("de", "DE"));
	assertNotNull(translator);
	String origin = "This text will be translated!";
	String translation = "Dieser Text wird uebersetzt werden!";
	translator.setTranslation(origin, new Locale("de", "DE"), translation);
	assertEquals(translation, translator.i18n(origin));
    }

    @Test
    public void testOneParameterInTranslation() {
	Translator.setDefault(new Locale("de", "DE"));
	assertNotNull(translator);
	String origin = "This text will be translated in {0,number,integer} lines!";
	String translation = "Dieser Text wird in {0,number,integer} Zeilen uebersetzt werden!";
	translator.setTranslation(origin, new Locale("de", "DE"), translation);
	assertEquals("Dieser Text wird in 3 Zeilen uebersetzt werden!", translator.i18n(origin, 3));
    }

    @Test
    public void testMultipleParametersInTranslation() {
	Translator.setDefault(new Locale("de", "DE"));
	assertNotNull(translator);
	String origin = "Switch {0,number,integer} and {1,number,integer}!";
	String translation = "Vertausche {1,number,integer} und {0,number,integer}!";
	translator.setTranslation(origin, new Locale("de", "DE"), translation);
	assertEquals("Vertausche 2 und 1!", translator.i18n(origin, 1, 2));
    }

    @Test
    public void testChoiceInTranslation() {
	Translator.setDefault(new Locale("de", "DE"));
	assertNotNull(translator);
	String origin = "Some files were opened: {0,choice,0#no files|1#one file|1<{0,number,integer} files}";
	String translation = "Es wurden Dateien geoeffnet: {0,choice,0#keine Datei|1#eine Datei|1<{0,number,integer} Dateien}";
	translator.setTranslation(origin, new Locale("de", "DE"), translation);
	assertEquals("Es wurden Dateien geoeffnet: keine Datei", translator.i18n(origin, 0));
	assertEquals("Es wurden Dateien geoeffnet: eine Datei", translator.i18n(origin, 1));
	assertEquals("Es wurden Dateien geoeffnet: 2 Dateien", translator.i18n(origin, 2));
	assertEquals("Es wurden Dateien geoeffnet: 3 Dateien", translator.i18n(origin, 3));
    }

    @Test
    public void testDateLocalizationInTranslation() throws Exception {
	Translator.setDefault(new Locale("de", "DE"));
	assertNotNull(translator);

	Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2009-07-07 12:00:00");

	String origin = "This test was create on {0,date}.";
	String translation = "Dieser Test wurde am {0,date} erstellt.";
	translator.setTranslation(origin, new Locale("de", "DE"), translation);

	Locale.setDefault(new Locale("de", "DE"));
	assertEquals("Dieser Test wurde am 07.07.2009 erstellt.", translator.i18n(origin, date));

	origin = "This test was create on {0,date,long}.";
	translation = "Dieser Test wurde am {0,date,long} erstellt.";
	translator.setTranslation(origin, new Locale("de", "DE"), translation);

	Locale.setDefault(new Locale("de", "DE"));
	assertEquals("Dieser Test wurde am 7. Juli 2009 erstellt.", translator.i18n(origin, date));

	origin = "This test was create on {0,date,short}.";
	translation = "Dieser Test wurde am {0,date,short} erstellt.";
	translator.setTranslation(origin, new Locale("de", "DE"), translation);

	Locale.setDefault(new Locale("de", "DE"));
	assertEquals("Dieser Test wurde am 07.07.09 erstellt.", translator.i18n(origin, date));
    }

    @Test
    public void testMultipleTranslationWithoutArgument() {
	Translator.setDefault(new Locale("de", "DE"));
	Translator.addAdditionalLocale(new Locale("vi", "VN"));
	Translator.addAdditionalLocale(new Locale("en", "US"));
	translator.setTranslation("English", new Locale("de", "DE"), "Deutsch");
	translator.setTranslation("English", new Locale("vi", "VN"), "Tieng Viet");
	translator.setTranslation("English", new Locale("en", "US"), "English");
	assertEquals("Deutsch (Tieng Viet) (English)", translator.i18n("English"));
    }

    @Test
    public void testMultipleTranslationWithArgument() throws Exception {
	Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2009-07-07 12:00:00");
	Translator.setDefault(new Locale("de", "DE"));
	Translator.addAdditionalLocale(new Locale("vi", "VN"));
	Translator.addAdditionalLocale(new Locale("en", "US"));
	translator.setTranslation("English: {0,date,short}", new Locale("de", "DE"), "Deutsch: {0,date,short}");
	translator.setTranslation("English: {0,date,short}", new Locale("vi", "VN"), "Tieng Viet: {0,date,short}");
	translator.setTranslation("English: {0,date,short}", new Locale("en", "US"), "English: {0,date,short}");
	System.out.println(translator.i18n("English: {0,date,short}", date));
	assertEquals("Deutsch: 07.07.09 " + "(Tieng Viet: 07/07/2009) " + "(English: 7/7/09)",
		translator.i18n("English: {0,date,short}", date));
    }

    @Test
    public void testMultipleLineTranslation() {
	translator.setTranslation("Line1\nLine2", new Locale("de"), "Zeile1\nZeile2");
	assertEquals("Zeile1\nZeile2", translator.translate("Line1\nLine2", new Locale("de")));
    }
}
