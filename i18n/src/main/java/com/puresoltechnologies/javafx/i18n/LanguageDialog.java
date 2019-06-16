/****************************************************************************
 *
 *   LanguageDialog.java
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

import java.util.Locale;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * This is an interactive language dialog. As soon as the language is changed.
 * The whole translation environment is reset and repainted with new
 * translations to see the effect. The dialog is non-modal and therefore, the
 * language can be changed on the fly during the normal work process.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class LanguageDialog extends Dialog<Locale> {

    private static final long serialVersionUID = -5526911970098174813L;

    private static final Translator translator = Translator.getTranslator(LanguageDialog.class);

    private final Label label = new Label("Language:");
    private final LocaleChooser languages = new LocaleChooser();
    private final Locale startLocale;

    public LanguageDialog() {
	setTitle("Translation");
	startLocale = Translator.getDefault();
	initUI();
    }

    private void initUI() {

	DialogPane dialogPane = getDialogPane();
	BorderPane borderPane = new BorderPane();
	dialogPane.setContent(borderPane);

	HBox languageBox = new HBox(5.0);

	languageBox.getChildren().addAll(label, languages);
	languages.valueProperty().addListener((oldValue, newValue, component) -> {
	    Translator.setDefault(newValue);
	});
	languages.setValue(startLocale);

	borderPane.setCenter(languageBox);
	dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

}
