/****************************************************************************
 *
 *   LanguageSetTest.java
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.jupiter.api.Test;

public class LanguageSetTest {

    @Test
    public void testAssumptions() {
	Locale locale = new Locale("de");
	assertEquals("de", locale.getLanguage());
	assertEquals("", locale.getCountry());
	assertEquals("", locale.getVariant());
    }

    @Test
    public void testLocale2String() {
	LanguageSet set = new LanguageSet();
	assertEquals("de", set.locale2String(new Locale("de")));
	assertEquals("en", set.locale2String(new Locale("en")));
	assertEquals("en_GB", set.locale2String(new Locale("en", "GB")));
	assertEquals("en_US", set.locale2String(new Locale("en", "US")));
	assertEquals("en_US_TT", set.locale2String(new Locale("en", "US", "TT")));
    }

    @Test
    public void testString2Locale() {
	LanguageSet set = new LanguageSet();
	assertEquals(new Locale("en"), set.string2Locale("en"));
	assertEquals(new Locale("en", "US"), set.string2Locale("en_US"));
	assertEquals(new Locale("en", "GB"), set.string2Locale("en_GB"));
	assertEquals(new Locale("en", "GB", "TT"), set.string2Locale("en_GB_TT"));
    }

    @Test
    public void testIllegalString2Locale() {
	assertThrows(RuntimeException.class, () -> {
	    LanguageSet set = new LanguageSet();
	    assertEquals(new Locale("en"), set.string2Locale("11_22_33_44"));
	});
    }

    @Test
    public void testDefaultConstructor() {
	LanguageSet set = new LanguageSet();
	assertEquals("", set.getSource());
	assertNotNull(set.getLocations());
	assertEquals(0, set.getLocations().size());
	assertEquals(0, set.getAvailableLanguages().size());
    }

    @Test
    public void testConstructors() {
	LanguageSet set = new LanguageSet("Source String");
	assertEquals("Source String", set.getSource());
	assertNotNull(set.getLocations());
	assertEquals(0, set.getLocations().size());
	assertEquals(0, set.getAvailableLanguages().size());
    }

    @Test
    public void testSettersAndGetters() {
	LanguageSet set = new LanguageSet();
	// settings...
	set.setSource("Source String");
	set.add(new Locale("de"), "Quellzeichenkette");

	LanguageSet translated = new LanguageSet("Source String");
	translated.add(new Locale("xx"), "XXX");
	translated.add(new Locale("yy"), "YYY");
	translated.addLocation(new SourceLocation("translated.java", 1, 1));
	set.add(translated);

	set.addLocation(new SourceLocation("TestFile.java", 1, 2));
	// no double entry(!):
	set.addLocation(new SourceLocation("TestFile.java", 1, 2));

	Set<SourceLocation> locations = new HashSet<>();
	locations.add(new SourceLocation("TestFile2.java", 2, 3));
	locations.add(new SourceLocation("TestFile3.java", 3, 4));
	set.addLocations(locations);

	// check for correct settings...
	assertEquals("Source String", set.getSource());
	Set<Locale> langs = set.getAvailableLanguages();
	assertEquals(3, langs.size());
	assertTrue(langs.contains(new Locale("de")));
	assertTrue(langs.contains(new Locale("xx")));
	assertTrue(langs.contains(new Locale("yy")));
	assertEquals("Quellzeichenkette", set.get(new Locale("de")));
	assertEquals("XXX", set.get(new Locale("xx")));
	assertEquals("YYY", set.get(new Locale("yy")));
	locations = set.getLocations();
	assertEquals(4, locations.size());
	assertTrue(locations.contains(new SourceLocation("translated.java", 1, 1)));
	assertTrue(locations.contains(new SourceLocation("TestFile.java", 1, 2)));
	assertTrue(locations.contains(new SourceLocation("TestFile2.java", 2, 3)));
	assertTrue(locations.contains(new SourceLocation("TestFile3.java", 3, 4)));

	// check clearLocations()...
	set.clearLocations();
	assertNotNull(set.getLocations());
	assertEquals(0, set.getLocations().size());
    }

    @Test
    public void testException() {
	assertThrows(IllegalArgumentException.class, () -> {
	    LanguageSet set = new LanguageSet("Source String1");
	    LanguageSet translated = new LanguageSet("Source String 2");
	    set.add(translated);
	    fail("Illegal ArgumentException was expected due to two different source strings!");
	});
    }

    @Test
    public void testEquals() {
	LanguageSet set1 = new LanguageSet();
	assertTrue(set1.equals(set1));
	assertFalse(set1.equals(null));
	assertFalse(set1.equals("Test"));

	LanguageSet set2 = new LanguageSet();
	assertTrue(set1.equals(set2));
	assertTrue(set2.equals(set1));
	set1.setSource("Source");
	assertFalse(set1.equals(set2));
	assertFalse(set2.equals(set1));
	set2.setSource("Source");
	assertTrue(set1.equals(set2));
	assertTrue(set2.equals(set1));
	set1.addLocation(new SourceLocation("source.java", 1, 2));
	assertFalse(set1.equals(set2));
	assertFalse(set2.equals(set1));
	set2.addLocation(new SourceLocation("source.java", 1, 2));
	assertTrue(set1.equals(set2));
	assertTrue(set2.equals(set1));

	assertTrue(set1.equals(set1));
	assertFalse(set1.equals(null));
	assertFalse(set1.equals("Test"));
    }

    @Test
    public void testHashCode() {
	LanguageSet set = new LanguageSet();
	assertTrue(set.hashCode() > 0);
    }

    @Test
    public void testClone() {
	LanguageSet origin = new LanguageSet("Source String");
	origin.add(new Locale("de"), "Quellzeichenkette");
	origin.addLocation(new SourceLocation("File.java", 1, 2));
	LanguageSet cloned = (LanguageSet) origin.clone();
	assertNotNull(cloned);
	assertEquals(origin, cloned);

	cloned.setSource("New Source String");
	assertFalse(origin.equals(cloned));
	cloned.setSource("Source String");
	assertTrue(origin.equals(cloned));

	cloned.add(new Locale("de"), "Neue Quellzeichenkette");
	assertEquals("Neue Quellzeichenkette", cloned.get(new Locale("de")));
	assertEquals("Quellzeichenkette", origin.get(new Locale("de")));
	assertFalse(origin.equals(cloned));
	cloned.add(new Locale("de"), "Quellzeichenkette");
	assertEquals("Quellzeichenkette", cloned.get(new Locale("de")));
	assertTrue(origin.equals(cloned));

	cloned.addLocation(new SourceLocation("File.java", 10, 3));
	assertFalse(origin.equals(cloned));
    }

    @Test
    public void testMarshallingAndUnmarshalling() throws Exception {
	File file = new File("LanguageSetMarshallerTest");
	LanguageSet translations = new LanguageSet("Source1");
	translations.add(new Locale("de", "DE"), "Quelle1");

	JAXBContext context = JAXBContext.newInstance(translations.getClass());
	assertNotNull(context);

	Marshaller marshaller = context.createMarshaller();
	assertNotNull(marshaller);
	marshaller.marshal(translations, file);
	assertTrue(file.exists());

	Unmarshaller unmarshaller = context.createUnmarshaller();
	LanguageSet unmarshalled = (LanguageSet) unmarshaller.unmarshal(file);
	assertNotSame(translations, unmarshalled);
	assertEquals(translations, unmarshalled);
	assertTrue(file.delete());
	assertFalse(file.exists());
    }
}
