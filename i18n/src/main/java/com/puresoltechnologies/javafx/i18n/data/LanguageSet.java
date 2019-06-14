/****************************************************************************
 *
 *   LanguageSet.java
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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class stores all translations of a single piece of text and all
 * additional information used for translation and statistics. It's collected
 * for example where the original string was found (a list of classes are
 * stored) and how often it was found in total. The original string is not
 * stored, because it's stored outside for example in TranslationsHash.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
@XmlRootElement(name = "translation")
@XmlAccessorType(XmlAccessType.FIELD)
public class LanguageSet implements Cloneable {

	private String source = "";
	private final Map<String, String> translations = new Hashtable<String, String>();
	private final Set<SourceLocation> locations = new HashSet<SourceLocation>();

	public LanguageSet() {
	}

	public LanguageSet(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void add(Locale locale, String translation) {
		translations.put(locale2String(locale), translation);
	}

	public String get(Locale locale) {
		return translations.get(locale2String(locale));
	}

	public void addLocations(Set<SourceLocation> locations) {
		for (SourceLocation location : locations) {
			addLocation(location);
		}
	}

	public void addLocation(SourceLocation location) {
		if (!locations.contains(location)) {
			locations.add(location);
		}
	}

	public Set<SourceLocation> getLocations() {
		return locations;
	}

	public void clearLocations() {
		locations.clear();
	}

	public void add(LanguageSet languageSet) {
		if (!source.equals(languageSet.source)) {
			throw new IllegalArgumentException("Source '" + languageSet.source
					+ "' can not be set to '" + source + "'!");
		}
		for (Locale locale : languageSet.getAvailableLanguages()) {
			add(locale, languageSet.get(locale));
		}
		addLocations(languageSet.getLocations());
	}

	public boolean has(Locale locale) {
		String translation = translations.get(locale2String(locale));
		return ((translation != null) && (!translation.isEmpty()));
	}

	public Set<Locale> getAvailableLanguages() {
		Set<Locale> locales = new HashSet<Locale>();
		for (String lang : translations.keySet()) {
			locales.add(string2Locale(lang));
		}
		return locales;
	}

	public Object clone() {
		try {
			LanguageSet cloned = (LanguageSet) super.clone();
			cloned.setSource(this.getSource());
			Field translations = cloned.getClass().getDeclaredField(
					"translations");
			translations.setAccessible(true);
			translations.set(cloned, new HashMap<String, String>());
			translations.setAccessible(false);

			Field locations = cloned.getClass().getDeclaredField("locations");
			locations.setAccessible(true);
			locations.set(cloned, new HashSet<SourceLocation>());
			locations.setAccessible(false);

			cloned.add(this);
			return cloned;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((locations == null) ? 0 : locations.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result
				+ ((translations == null) ? 0 : translations.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LanguageSet other = (LanguageSet) obj;
		if (locations == null) {
			if (other.locations != null) {
				return false;
			}
		} else if (!locations.equals(other.locations)) {
			return false;
		}
		if (source == null) {
			if (other.source != null) {
				return false;
			}
		} else if (!source.equals(other.source)) {
			return false;
		}
		if (translations == null) {
			if (other.translations != null) {
				return false;
			}
		} else if (!translations.equals(other.translations)) {
			return false;
		}
		return true;
	}

	String locale2String(Locale locale) {
		String language = locale.getLanguage();
		String country = locale.getCountry();
		String variant = locale.getVariant();
		String result = language;
		if (!country.isEmpty()) {
			result += "_" + country;
		}
		if (!variant.isEmpty()) {
			result += "_" + variant;
		}
		return result;
	}

	Locale string2Locale(String language) {
		String splits[] = language.split("_");
		if (splits.length == 1) {
			return new Locale(splits[0]);
		} else if (splits.length == 2) {
			return new Locale(splits[0], splits[1]);
		} else if (splits.length == 3) {
			return new Locale(splits[0], splits[1], splits[2]);
		} else {
			throw new RuntimeException(
					"Illegal form of locale string found in '" + language + "'");
		}
	}

}
