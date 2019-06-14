/****************************************************************************
 *
 *   KeyStrokeUpdater.java
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

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * This class includes a mechanism to add in-situ updates for GUI elements to
 * translate their accelerator key after a language change.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */

public class KeyStrokeUpdater {

	private final Map<WeakReference<?>, LanguageChangeListener> listeners = new WeakHashMap<WeakReference<?>, LanguageChangeListener>();

	/**
	 * This method adds a JMenuItem to the updating process.
	 * 
	 * @param text
	 *            is the text to be translated and shown in the border title.
	 * @param translator
	 *            is the translator to use for translation.
	 * @param item
	 *            is the JMenuItem to be translated.
	 * @param params
	 *            is the list of parameters to be used as parameters for the
	 *            translation string.
	 * @return The item itself is returned to use this method as method
	 *         argument.
	 */
	public JMenuItem i18n(final String text, final Translator translator,
			final JMenuItem item, final Object... params) {
		final WeakReference<JMenuItem> referent = new WeakReference<JMenuItem>(
				item);
		LanguageChangeListener listener = new LanguageChangeListener() {
			@Override
			public void translationChanged(Translator translator) {
				JMenuItem item = referent.get();
				if (item != null) {
					item.setAccelerator(KeyStroke.getKeyStroke(translator.i18n(
							text, params)));
				}
			}
		};
		add(referent, translator, listener);
		return item;

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
