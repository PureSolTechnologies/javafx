/****************************************************************************
 *
 *   SingleLanguageTranslations.java
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
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is for storing translations for a single language. Therefore, it
 * is only a wrapper for a ConcurrentMap object.
 *
 * This class is thread safe.
 *
 * @author Rick-Rainer Ludwig
 *
 */
@XmlRootElement(name = "translations")
@XmlAccessorType(XmlAccessType.FIELD)
public class SingleLanguageTranslations implements Cloneable {

    private final ConcurrentMap<String, String> translations = new ConcurrentHashMap<>();

    public SingleLanguageTranslations() {
    }

    /**
     * Sets a new translation. An old translation will be overwritten.
     *
     * @param source      is the source.
     * @param translation contains the translation.
     */
    public void set(String source, String translation) {
	translations.put(source, translation);
    }

    /**
     * Adds a translation if the origin is not present. Already existing
     * translations are not overwritten!
     *
     * @param source      is the source translation.
     * @param translation is the translation.
     */
    public void add(String source, String translation) {
	translations.putIfAbsent(source, translation);
    }

    public String get(String source) {
	return translations.get(source);
    }

    @Override
    public String toString() {
	StringBuffer result = new StringBuffer();
	for (Entry<String, String> translation : translations.entrySet()) {
	    result.append(translation.getKey()).append(" --> ").append(translation.getValue()).append("\n");
	}
	return result.toString();
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
	result = (prime * result) + ((translations == null) ? 0 : translations.hashCode());
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
	SingleLanguageTranslations other = (SingleLanguageTranslations) obj;
	if (translations == null) {
	    if (other.translations != null) {
		return false;
	    }
	} else if (!translations.equals(other.translations)) {
	    return false;
	}
	return true;
    }

    @Override
    public Object clone() {
	try {
	    SingleLanguageTranslations cloned = (SingleLanguageTranslations) super.clone();
	    Field translationsField;
	    translationsField = cloned.getClass().getDeclaredField("translations");
	    translationsField.setAccessible(true);
	    translationsField.set(cloned, new ConcurrentHashMap<String, String>());
	    for (String source : this.translations.keySet()) {
		cloned.translations.put(source, this.translations.get(source));
	    }
	    return cloned;
	} catch (SecurityException e) {
	    throw new RuntimeException(e);
	} catch (NoSuchFieldException e) {
	    throw new RuntimeException(e);
	} catch (IllegalArgumentException e) {
	    throw new RuntimeException(e);
	} catch (IllegalAccessException e) {
	    throw new RuntimeException(e);
	} catch (CloneNotSupportedException e) {
	    throw new RuntimeException(e);
	}
    }

    public void removeLineBreaks() {
	for (String source : translations.keySet()) {
	    String translation = translations.get(source);
	    translations.remove(source);
	    source = source.replaceAll("\\n", "\\\\n");
	    translation = translation.replaceAll("\\n", "\\\\n");
	    translations.put(source, translation);
	}
    }

    public void addLineBreaks() {
	for (Entry<String, String> t : translations.entrySet()) {
	    String translation = t.getValue();
	    translations.remove(t.getKey());
	    String source = t.getKey().replaceAll("\\\\n", "\n");
	    translation = translation.replaceAll("\\\\n", "\n");
	    translations.put(source, translation);
	}
    }
}
