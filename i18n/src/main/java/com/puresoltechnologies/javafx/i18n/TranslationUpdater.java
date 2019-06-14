/****************************************************************************
 *
 *   TranslationUpdater.java
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

import java.awt.Dialog;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

/**
 * This class includes a mechanism to add in-situ updates for GUI elements to
 * translate their content after a language change.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class TranslationUpdater {

	private final Map<WeakReference<?>, LanguageChangeListener> listeners = new HashMap<WeakReference<?>, LanguageChangeListener>();

	/**
	 * This method adds an AbstractButton to the updating process.
	 * 
	 * @param text
	 *            is the text to be translated and shown in the border title.
	 * @param translator
	 *            is the translator to use for translation.
	 * @param button
	 *            is the abstract button to be translated.
	 * @param params
	 *            is the list of parameters to be used as parameters for the
	 *            translation string.
	 * @return The button itself is returned to use this method as method
	 *         argument.
	 */
	public AbstractButton i18n(final String text, final Translator translator,
			AbstractButton button, final Object... params) {
		final WeakReference<AbstractButton> referent = new WeakReference<AbstractButton>(
				button);
		LanguageChangeListener listener = new LanguageChangeListener() {
			@Override
			public void translationChanged(Translator translator) {
				AbstractButton button = referent.get();
				if (button != null) {
					String translation = translator.i18n(text, params);
					button.setText(translation);
					if ((translation != null) && (translation.length() > 0)) {
						button.setMnemonic(translation.toCharArray()[0]);
					} else {
						button.setMnemonic((char) 0);
					}
				}
			}
		};
		add(referent, translator, listener);
		return button;
	}

	/**
	 * This method adds a JLabel to the updating process.
	 * 
	 * @param text
	 *            is the text to be translated and shown in the border title.
	 * @param translator
	 *            is the translator to use for translation.
	 * @param label
	 *            is the JLabel to be translated.
	 * @param params
	 *            is the list of parameters to be used as parameters for the
	 *            translation string.
	 * @return The label itself is returned to use this method as method
	 *         argument.
	 */
	public JLabel i18n(final String text, final Translator translator,
			JLabel label, final Object... params) {
		final WeakReference<JLabel> referent = new WeakReference<JLabel>(label);
		LanguageChangeListener listener = new LanguageChangeListener() {
			@Override
			public void translationChanged(Translator translator) {
				JLabel label = referent.get();
				if (label != null) {
					label.setText(translator.i18n(text, params));
				}
			}
		};
		add(referent, translator, listener);
		return label;
	}

	/**
	 * This method adds a Dialog to the updating process.
	 * 
	 * @param text
	 *            is the text to be translated and shown in the border title.
	 * @param translator
	 *            is the translator to use for translation.
	 * @param dialog
	 *            is the JLabel to be translated.
	 * @param params
	 *            is the list of parameters to be used as parameters for the
	 *            translation string.
	 * @return The label itself is returned to use this method as method
	 *         argument.
	 */
	public Dialog i18n(final String text, final Translator translator,
			Dialog dialog, final Object... params) {
		final WeakReference<Dialog> referent = new WeakReference<Dialog>(dialog);
		LanguageChangeListener listener = new LanguageChangeListener() {
			@Override
			public void translationChanged(Translator translator) {
				Dialog dialog = referent.get();
				if (dialog != null) {
					dialog.setTitle(translator.i18n(text, params));
				}
			}
		};
		add(referent, translator, listener);
		return dialog;
	}

	/**
	 * This method is used to add a new titled border object to the updating
	 * process.
	 * 
	 * @param text
	 *            is the text to be translated and shown in the border title.
	 * @param translator
	 *            is the translator to use for translation.
	 * @param component
	 *            is the component, where the title is bound to.
	 * @param titledBorder
	 *            is the actual titled border to be translated.
	 * @param params
	 *            is the list of parameters to be used as parameters for the
	 *            translation string.
	 * @return The border itself is returned to use this method as method
	 *         argument.
	 */
	public TitledBorder i18n(final String text, final Translator translator,
			final JComponent component, TitledBorder titledBorder,
			final Object... params) {
		final WeakReference<JComponent> componentReferent = new WeakReference<JComponent>(
				component);
		final WeakReference<TitledBorder> borderReferent = new WeakReference<TitledBorder>(
				titledBorder);
		LanguageChangeListener listener = new LanguageChangeListener() {
			@Override
			public void translationChanged(Translator translator) {
				JComponent component = componentReferent.get();
				TitledBorder titledBorder = borderReferent.get();
				if ((component != null) && (titledBorder != null)) {
					titledBorder.setTitle(translator.i18n(text, params));
					component.repaint();
				}
			}
		};
		add(borderReferent, translator, listener);
		return titledBorder;
	}

	private void add(WeakReference<?> referent, Translator translator,
			LanguageChangeListener listener) {
		cleanup();
		listeners.put(referent, listener);
		translator.addLanguageChangeListener(listener);
		listener.translationChanged(translator);
	}

	private void cleanup() {
		for (Iterator<WeakReference<?>> keyIterator = listeners.keySet()
				.iterator(); keyIterator.hasNext();) {
			WeakReference<?> key = keyIterator.next();
			if (key.get() == null) {
				listeners.remove(key);
			}
		}
	}

	/**
	 * This method is used for testing purposes.
	 * 
	 * @return
	 */
	Map<WeakReference<?>, LanguageChangeListener> getListeners() {
		cleanup();
		return listeners;
	}
}
