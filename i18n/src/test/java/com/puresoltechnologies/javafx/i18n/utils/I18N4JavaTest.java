/****************************************************************************
 *
 *   I18N4JavaTest.java
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

public class I18N4JavaTest {

    @Test
    public void testGetISOLanguages() {
	List<String> languages = I18N4Java.getISOLanguages();
	assertNotNull(languages);
	assertTrue(languages.size() > 0);
    }

    @Test
    public void testGetAvailableLocales() {
	List<Locale> locales = I18N4Java.getAvailableLocales();
	assertNotNull(locales);
	assertTrue(locales.size() > 0);
    }

    @Test
    public void testGetAvailableLocaleNames() {
	List<String> localeNames = I18N4Java.getAvailableLocaleNames();
	assertNotNull(localeNames);
	assertTrue(localeNames.size() > 0);
    }

    @Test
    public void testGetLanguageLocales() {
	List<Locale> languageLocales = I18N4Java.getLanguageLocales();
	assertNotNull(languageLocales);
	assertTrue(languageLocales.size() > 0);
    }

    @Test
    public void testGetImplementationLocale() {
	assertEquals(Locale.US, I18N4Java.getImplementationLocale());
    }
}
