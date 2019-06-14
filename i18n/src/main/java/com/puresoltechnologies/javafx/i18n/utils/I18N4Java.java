/****************************************************************************
 *
 *   I18N4Java.java
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * This class provides some easy and efficient ways to perform standard
 * operations related to Java's I18n framework.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class I18N4Java {

	private static final Locale implementationLocale;
	static {
		try {
			InputStream inputStream = I18N4Java.class
					.getResourceAsStream("/config/i18n4java.properties");
			if (inputStream != null) {
				try {
					Properties properties = new Properties();
					properties.load(inputStream);
					String language = (String) properties
							.get("i18n4java.implementation.language");
					String country = (String) properties
							.get("i18n4java.implementation.country");
					String variant = (String) properties
							.get("i18n4java.implementation.variant");
					if ((language != null) && (!language.isEmpty())) {
						if ((country != null) && (!country.isEmpty())) {
							if ((variant != null) && (!variant.isEmpty())) {
								implementationLocale = new Locale(language,
										country, variant);
							} else {
								implementationLocale = new Locale(language,
										country);
							}
						} else {
							implementationLocale = new Locale(language);
						}
					} else {
						implementationLocale = Locale.US;
					}
				} finally {
					inputStream.close();
				}
			} else {
				implementationLocale = Locale.US;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<String> getISOLanguages() {
		List<String> isoLanguages = new ArrayList<String>();
		Collections.addAll(isoLanguages, Locale.getISOLanguages());
		Collections.sort(isoLanguages);
		return isoLanguages;
	}

	public static List<Locale> getAvailableLocales() {
		List<Locale> locales = new ArrayList<Locale>();
		Collections.addAll(locales, Locale.getAvailableLocales());
		return locales;
	}

	public static List<String> getAvailableLocaleNames() {
		List<String> localeNames = new ArrayList<String>();
		for (Locale locale : Locale.getAvailableLocales()) {
			localeNames.add(locale.toString());
		}
		Collections.sort(localeNames);
		return localeNames;
	}

	public static List<Locale> getLanguageLocales() {
		List<Locale> isoLanguages = new ArrayList<Locale>();
		for (String isoLanguage : getISOLanguages()) {
			isoLanguages.add(new Locale(isoLanguage));
		}
		return isoLanguages;
	}

	public static Locale getImplementationLocale() {
		return implementationLocale;
	}

}
