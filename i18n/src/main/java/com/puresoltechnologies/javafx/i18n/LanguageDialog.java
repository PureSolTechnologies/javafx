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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 * This is an interactive language dialog. As soon as the language is changed.
 * The whole translation environment is reset and repainted with new
 * translations to see the effect. The dialog is non-modal and therefore, the
 * language can be changed on the fly during the normal work process.
 * 
 * To make the application able to react accordingly, all elements which need to
 * be updated on the fly need to be added to the updating process by object of
 * the classes TranslationUpdater and KeyStrokeUpdater.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class LanguageDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -5526911970098174813L;

	private static final Translator translator = Translator
			.getTranslator(LanguageDialog.class);

	private final TranslationUpdater translationUpdater = new TranslationUpdater();

	private final JLabel label = new JLabel();
	private final LocaleChooser languages = new LocaleChooser();
	private final JButton ok = new JButton();
	private final JButton cancel = new JButton();
	private final Locale startLocale;

	public LanguageDialog(JFrame frame) {
		super(frame, "Translation", false);
		translationUpdater.i18n("Translation", translator, this);
		startLocale = Translator.getDefault();
		initUI();
	}

	private void initUI() {
		getRootPane().registerKeyboardAction(this, "ESC",
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JRootPane.WHEN_IN_FOCUSED_WINDOW);

		Container panel = getContentPane();
		panel.setLayout(new BorderLayout());

		JPanel languagePanel = new JPanel();
		languagePanel.setLayout(new BoxLayout(languagePanel,
				BoxLayout.PAGE_AXIS));

		languagePanel.add(label);
		translationUpdater.i18n("Language:", translator, label);

		languagePanel.add(languages);
		languages.addActionListener(this);
		languages.setSelectedLocale(startLocale);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		buttonPanel.add(ok);
		translationUpdater.i18n("OK", translator, ok);
		ok.addActionListener(this);
		getRootPane().setDefaultButton(ok);

		buttonPanel.add(cancel);
		translationUpdater.i18n("Cancel", translator, cancel);
		cancel.addActionListener(this);

		panel.add(languagePanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(getParent());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			setVisible(false);
		} else if (e.getSource() == cancel) {
			Translator.setDefault(startLocale);
			setVisible(false);
		} else if (e.getSource() == languages) {
			Translator.setDefault(languages.getSelectedLocale());
		} else if (e.getSource() == getRootPane()) {
			setVisible(false);
		}
	}

}
