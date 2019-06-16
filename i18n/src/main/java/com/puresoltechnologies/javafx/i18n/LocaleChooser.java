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
import java.util.Locale;

import com.puresoltechnologies.javafx.i18n.utils.I18N4Java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * This class provides a combobox with all available locales for choosing.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class LocaleChooser extends ComboBox<Locale> {

    private final ObservableList<Locale> availableLocales = FXCollections.observableArrayList();

    public LocaleChooser() {
	super();
	setItems(availableLocales);
	availableLocales.addAll(I18N4Java.getAvailableLocales());
	Collections.sort(availableLocales, (arg0, arg1) -> arg0.toString().compareTo(arg1.toString()));
    }
}
