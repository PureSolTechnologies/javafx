/****************************************************************************
 *
 *   TRFile.java
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class TRFile {

    /**
     * This method calculates the resource path to a context translation file. The
     * language information from the locale is used to invoke another getTrResource
     * method to get the name.
     *
     * @see I18NFile#getResource(File)
     * @param context is the context name to use. In normal case its
     *                class.getName().
     * @param locale  is a locale as language specifier to be used.
     * @return A String is returned with a path to the translation file. String is
     *         used and not(!) File to due the issue that resources within JARs are
     *         to be specified with normal '/' slash separators.
     */
    public static String getResourceName(String context, Locale locale) {
	if ((locale.getLanguage() != null) && (!locale.getLanguage().isEmpty())) {
	    if ((locale.getCountry() != null) && (!locale.getCountry().isEmpty())) {
		return getResourceName(context, locale.getLanguage() + "_" + locale.getCountry());
	    } else {
		return getResourceName(context, locale.getLanguage());
	    }
	} else {
	    throw new RuntimeException("Locale '" + locale.toString() + "' is not valid!");
	}
    }

    /**
     * This method calculates the resource path to a context translation file.
     *
     * @param context  is the context name to use. In normal case its
     *                 class.getName().
     * @param language is the language to look for.
     * @return A String is returned with a path to the translation file. String is
     *         used and not(!) File to due the issue that resources within JARs are
     *         to be specified with normal '/' slash separators.
     */
    private static String getResourceName(String context, String language) {
	return "/" + context.replaceAll("\\.", "/") + "." + language + ".tr";
    }

    public static void write(File file, SingleLanguageTranslations translations) throws IOException {
	try {
	    File directory = file.getParentFile();
	    if (!directory.exists()) {
		if (!directory.mkdirs()) {
		    throw new IOException("Directory '" + directory + "' not created.");
		}
	    }
	    translations = (SingleLanguageTranslations) translations.clone();
	    translations.removeLineBreaks();
	    JAXBContext context = JAXBContext.newInstance(translations.getClass());
	    Marshaller marshaller = context.createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    marshaller.marshal(translations, file);
	} catch (JAXBException e) {
	    throw new IOException(e);
	}
    }

    public static SingleLanguageTranslations read(File file) throws IOException {
	try {
	    if (!file.exists()) {
		throw new FileNotFoundException("File " + file.getPath() + " could not be found!");
	    }
	    JAXBContext context = JAXBContext.newInstance(SingleLanguageTranslations.class);
	    Unmarshaller unmarshaller = context.createUnmarshaller();
	    SingleLanguageTranslations translations = (SingleLanguageTranslations) unmarshaller.unmarshal(file);
	    translations.addLineBreaks();
	    return translations;
	} catch (JAXBException e) {
	    throw new IOException(e);
	}
    }

    public static SingleLanguageTranslations read(InputStream inputStream) throws IOException {
	try {
	    if (inputStream == null) {
		throw new IOException("Input stream was not available!");
	    }
	    JAXBContext context = JAXBContext.newInstance(SingleLanguageTranslations.class);
	    Unmarshaller unmarshaller = context.createUnmarshaller();
	    SingleLanguageTranslations translations = (SingleLanguageTranslations) unmarshaller.unmarshal(inputStream);
	    translations.addLineBreaks();
	    return translations;
	} catch (JAXBException e) {
	    throw new IOException(e);
	}
    }
}
