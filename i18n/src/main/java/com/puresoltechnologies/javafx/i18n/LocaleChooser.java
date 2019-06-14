/****************************************************************************
 *
 *   LocaleChooser.java
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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.swing.JComboBox;

import com.puresoltechnologies.javafx.i18n.utils.I18N4Java;

/**
 * This class provides a combobox with all available locales for choosing.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class LocaleChooser extends JComboBox {

	private static final long serialVersionUID = -5751261750747502182L;

	private final List<Locale> availableLocales = I18N4Java
			.getAvailableLocales();

	public LocaleChooser() {
		super();
		insertLocales();
	}

	private void insertLocales() {
		Collections.sort(availableLocales, new Comparator<Locale>() {
			@Override
			public int compare(Locale arg0, Locale arg1) {
				return arg0.toString().compareTo(arg1.toString());
			}
		});
		for (Locale locale : availableLocales) {
			addItem(locale.toString() + " - " + locale.getDisplayLanguage()
					+ " / " + locale.getDisplayCountry());
		}
	}

	public Locale getSelectedLocale() {
		return availableLocales.get(getSelectedIndex());
	}

	public void setSelectedLocale(Locale locale) {
		for (int index = 0; index < availableLocales.size(); index++) {
			if (availableLocales.get(index).equals(locale)) {
				setSelectedIndex(index);
				break;
			}
		}
	}
}
