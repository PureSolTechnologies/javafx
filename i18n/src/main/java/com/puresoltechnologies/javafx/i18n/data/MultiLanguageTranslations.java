/****************************************************************************
 *
 *   MultiLanguageTranslations.java
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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This hash contains the translations for I18N. Its purpose is to hold
 * translations for multiple translations to support the multi language support
 * for training sessions.
 *
 * @author Rick-Rainer Ludwig
 *
 */
@XmlRootElement(name = "internationalization")
@XmlAccessorType(XmlAccessType.FIELD)
public class MultiLanguageTranslations implements Cloneable {

    private final Hashtable<String, LanguageSet> translations = new Hashtable<>();

    /**
     * Creates an initial translations hash with starting data provided.
     *
     * @param source the source string.
     * @param file   is the file
     * @param line   is the line in file.
     * @return A {@link MultiLanguageTranslations} object is returned.
     */
    public static MultiLanguageTranslations create(String source, String file, int line) {
	MultiLanguageTranslations hash = new MultiLanguageTranslations();
	hash.add(source, file, line);
	return hash;
    }

    public MultiLanguageTranslations() {
	super();
    }

    @SuppressWarnings("unchecked")
    public void setTranslations(Hashtable<String, LanguageSet> translations) {
	if (translations == null) {
	    throw new IllegalArgumentException("translations must no be null!");
	}
	this.translations.clear();
	this.translations.putAll((Hashtable<String, LanguageSet>) translations.clone());
    }

    @SuppressWarnings("unchecked")
    public Hashtable<String, LanguageSet> getTranslations() {
	return (Hashtable<String, LanguageSet>) translations.clone();
    }

    public LanguageSet getTranslations(String source) {
	LanguageSet languageSet = translations.get(source);
	if (languageSet == null) {
	    return null;
	}
	return (LanguageSet) languageSet.clone();
    }

    public LanguageSet get(String source) {
	if (!translations.containsKey(source)) {
	    return null;
	}
	return translations.get(source);
    }

    public String get(String source, Locale locale) {
	LanguageSet set = get(source);
	if (set == null) {
	    return source;
	}
	if (!set.has(locale)) {
	    return source;
	}
	return translations.get(source).get(locale);
    }

    public void add(String source) {
	if (!translations.containsKey(source)) {
	    translations.put(source, new LanguageSet(source));
	}
    }

    public void add(String source, Locale locale, String translation) {
	add(source);
	translations.get(source).add(locale, translation);
    }

    public void add(String source, String file, int line) {
	add(source);
	addLocation(source, new SourceLocation(file, line));
    }

    public void add(MultiLanguageTranslations translations) {
	if (translations != null) {
	    for (String source : translations.getSources()) {
		add(source, translations.get(source));
	    }
	}
    }

    public void add(String source, LanguageSet translation) {
	add(source);
	translations.get(source).add(translation);
    }

    public void addLocation(String source, SourceLocation location) {
	add(source);
	translations.get(source).addLocation(location);
    }

    public void addLocations(String source, Set<SourceLocation> locations) {
	add(source);
	translations.get(source).addLocations(locations);
    }

    public boolean has(String oldSource, Locale locale) {
	LanguageSet languageSet = translations.get(oldSource);
	if (languageSet == null) {
	    return false;
	}
	return languageSet.has(locale);
    }

    public Set<SourceLocation> getLocations(String source) {
	LanguageSet languageSet = translations.get(source);
	if (languageSet == null) {
	    return new HashSet<>();
	}
	return languageSet.getLocations();
    }

    public void removeLocations() {
	for (String source : translations.keySet()) {
	    translations.get(source).clearLocations();
	}
    }

    public boolean containsSource(String original) {
	return translations.containsKey(original);
    }

    public Set<String> getSources() {
	return translations.keySet();
    }

    public Set<Locale> getAvailableLanguages() {
	Set<Locale> availableLanguages = new HashSet<>();
	Set<String> sources = translations.keySet();
	for (String source : sources) {
	    for (Locale language : getAvailableLanguages(source)) {
		if (!availableLanguages.contains(language)) {
		    availableLanguages.add(language);
		}
	    }
	}
	return availableLanguages;
    }

    public Set<Locale> getAvailableLanguages(String source) {
	return translations.get(source).getAvailableLanguages();
    }

    public boolean hasSources() {
	return (translations.size() > 0);
    }

    public void removeSourcesWithoutLocation() {
	ArrayList<String> toRemove = new ArrayList<>();
	for (String source : translations.keySet()) {
	    LanguageSet languageSet = translations.get(source);
	    if (languageSet.getLocations().size() == 0) {
		toRemove.add(source);
	    }
	}
	for (String source : toRemove) {
	    translations.remove(source);
	}
    }

    public void removeLineBreaks() {
	Enumeration<String> sources = translations.keys();
	while (sources.hasMoreElements()) {
	    String source = sources.nextElement();
	    LanguageSet languageSet = translations.get(source);
	    translations.remove(source);
	    source = languageSet.getSource();
	    source = source.replaceAll("\\n", "\\\\n");
	    translations.put(source, languageSet);
	    languageSet.setSource(source);
	    for (Locale language : languageSet.getAvailableLanguages()) {
		String translation = languageSet.get(language);
		if (translation != null) {
		    translation = translation.replaceAll("\\n", "\\\\n");
		    languageSet.add(language, translation);
		}
	    }
	}
    }

    public void addLineBreaks() {
	Enumeration<String> sources = translations.keys();
	while (sources.hasMoreElements()) {
	    String source = sources.nextElement();
	    LanguageSet languageSet = translations.get(source);
	    translations.remove(source);
	    source = languageSet.getSource();
	    source = source.replaceAll("\\\\n", "\n");
	    translations.put(source, languageSet);
	    languageSet.setSource(source);
	    for (Locale language : languageSet.getAvailableLanguages()) {
		String translation = languageSet.get(language);
		if (translation != null) {
		    translation = translation.replaceAll("\\\\n", "\n");
		    languageSet.add(language, translation);
		}
	    }
	}
    }

    public boolean isTranslationFinished() {
	Set<Locale> languages = getAvailableLanguages();
	if (languages.size() == 0) {
	    return false;
	}
	for (Locale language : languages) {
	    if (!isTranslationFinished(language)) {
		return false;
	    }
	}
	return true;
    }

    public boolean isTranslationFinished(Locale language) {
	for (String source : getSources()) {
	    LanguageSet languageSet = getTranslations(source);
	    if (!languageSet.has(language)) {
		return false;
	    }
	}
	return true;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = (prime * result) + translations.hashCode();
	return result;
    }

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
	MultiLanguageTranslations other = (MultiLanguageTranslations) obj;
	if (!translations.equals(other.translations)) {
	    return false;
	}
	return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
	try {
	    MultiLanguageTranslations cloned = (MultiLanguageTranslations) super.clone();
	    cloned.translations.putAll((Hashtable<String, LanguageSet>) this.translations.clone());
	    return cloned;
	} catch (CloneNotSupportedException e) {
	    throw new RuntimeException(e);
	}
    }
}
