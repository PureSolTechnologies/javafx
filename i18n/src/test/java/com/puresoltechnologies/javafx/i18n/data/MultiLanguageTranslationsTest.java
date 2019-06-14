/****************************************************************************
 *
 *   MultiLanguageTranslationsTest.java
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

package com.puresoltechnologies.javafx.i18n.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class MultiLanguageTranslationsTest {

    @Test
    public void testDefaultConstructor() {
	MultiLanguageTranslations translations = new MultiLanguageTranslations();
	assertEquals(0, translations.getAvailableLanguages().size());
	assertEquals(0, translations.getSources().size());
	assertFalse(translations.hasSources());
    }

    @Test
    public void testFrom() {
	MultiLanguageTranslations translations = MultiLanguageTranslations.create("Source", "File.java", 123);
	assertNotNull(translations);
	assertEquals(0, translations.getAvailableLanguages().size());
	assertEquals(1, translations.getSources().size());
	assertTrue(translations.hasSources());
	LanguageSet languageSet = translations.get("Source");
	assertNotNull(languageSet);
	assertEquals(0, languageSet.getAvailableLanguages().size());
	Set<SourceLocation> locations = languageSet.getLocations();
	assertNotNull(locations);
	assertEquals(1, locations.size());
    }

    @Test
    public void testSettersAndGetters() {
	MultiLanguageTranslations translations = new MultiLanguageTranslations();
	translations.add("Source1");
	translations.addLocation("Source1", new SourceLocation("File1.java", 1, 2));
	translations.add("Source2", "File2.java", 2);
	translations.add("Source3", new Locale("de"), "Quelle3");
	translations.addLocation("Source3", new SourceLocation("File3.java", 3, 4));
	Set<SourceLocation> locations = new HashSet<>();
	locations.add(new SourceLocation("File3.java", 3, 4));
	locations.add(new SourceLocation("File3.java", 5, 6));
	locations.add(new SourceLocation("File3_1.java", 1, 2));
	translations.addLocations("Source3", locations);

	assertTrue(translations.getTranslations().size() > 0);
	assertTrue(translations.hasSources());

	assertTrue(translations.containsSource("Source1"));
	assertEquals(1, translations.getAvailableLanguages().size());
	assertEquals(3, translations.getSources().size());
	assertEquals(0, translations.getAvailableLanguages("Source1").size());
	assertEquals(1, translations.getLocations("Source1").size());
	LanguageSet languageSet = translations.get("Source1");
	assertNotNull(languageSet);
	assertEquals(0, languageSet.getAvailableLanguages().size());
	locations = languageSet.getLocations();
	assertNotNull(locations);
	assertEquals(1, locations.size());

	assertTrue(translations.containsSource("Source2"));
	assertEquals(0, translations.getAvailableLanguages("Source2").size());
	assertEquals(1, translations.getLocations("Source2").size());
	languageSet = translations.get("Source2");
	assertNotNull(languageSet);
	assertEquals(0, languageSet.getAvailableLanguages().size());
	locations = languageSet.getLocations();
	assertNotNull(locations);
	assertEquals(1, locations.size());

	assertTrue(translations.containsSource("Source3"));
	assertEquals(1, translations.getAvailableLanguages("Source3").size());
	assertEquals(3, translations.getLocations("Source3").size());
	languageSet = translations.get("Source3");
	assertNotNull(languageSet);
	assertEquals(1, languageSet.getAvailableLanguages().size());
	locations = languageSet.getLocations();
	assertNotNull(locations);
	assertEquals(3, locations.size());

	translations.removeLocations();
	assertEquals(0, translations.getLocations("Source1").size());
	assertEquals(0, translations.getLocations("Source2").size());
	assertEquals(0, translations.getLocations("Source3").size());

	Hashtable<String, LanguageSet> translation = new Hashtable<>();
	translation.put("Test", new LanguageSet());
	translation.get("Test").add(new Locale("de"), "Test");
	translations.setTranslations(translation);
	assertNotSame(translation, translations.getTranslations());
	assertEquals(translation, translations.getTranslations());
    }

    @Test
    public void testEquals() {
	MultiLanguageTranslations translations = new MultiLanguageTranslations();
	assertTrue(translations.equals(translations));
	assertFalse(translations.equals(null));
    }

    @Test
    public void testClone() {
	MultiLanguageTranslations origin = MultiLanguageTranslations.create("Source1", "File.java", 123);
	MultiLanguageTranslations cloned = (MultiLanguageTranslations) origin.clone();
	assertNotSame(origin, cloned);
	assertEquals(origin, cloned);
    }

    @Test
    public void testSetAndGetTranslation() {
	MultiLanguageTranslations hash = new MultiLanguageTranslations();
	assertEquals("English", hash.get("English", new Locale("de")));

	hash.add("English", new Locale("de"), "Deutsch");
	hash.add("English", new Locale("vi"), "Tieng Viet");

	assertEquals("English", hash.get("English", new Locale("")));
	assertEquals("Deutsch", hash.get("English", new Locale("de")));
	assertEquals("Tieng Viet", hash.get("English", new Locale("vi")));
    }
}
