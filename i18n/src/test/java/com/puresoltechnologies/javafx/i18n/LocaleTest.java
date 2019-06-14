/****************************************************************************
 *
 *   LocaleTest.java
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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.Vector;

import org.junit.jupiter.api.Test;

public class LocaleTest {

    @Test
    public void testAvailableLocales() {
	Locale locales[] = Locale.getAvailableLocales();
	Vector<String> localeShortCuts = new Vector<>();
	for (Locale locale : locales) {
	    localeShortCuts.add(locale.toString());
	    System.out.println(locale.toString() + "\t" + locale.getDisplayName() + ":\t" + locale.getDisplayLanguage()
		    + "\t" + locale.getDisplayCountry() + "\t" + locale.getDisplayVariant());
	}
	assertTrue(localeShortCuts.contains("de_DE"));
	assertTrue(localeShortCuts.contains("en_GB"));
	assertTrue(localeShortCuts.contains("en_US"));
	assertTrue(localeShortCuts.contains("vi_VN"));
	assertTrue(localeShortCuts.contains("zh_TW"));
    }
}
