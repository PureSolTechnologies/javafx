/****************************************************************************
 *
 *   TranslationUpdaterTest.java
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.junit.jupiter.api.Test;

public class TranslationUpdaterTest {

    private static final Translator translator = Translator.getTranslator(TranslationUpdaterTest.class);

    @Test
    public void testInstance() {
	assertNotNull(new TranslationUpdater());
    }

    @Test
    public void testWeakReferencesAbstractButton() {
	TranslationUpdater o = new TranslationUpdater();
	JMenuItem item = new JMenuItem();
	o.i18n("JMenuItem", translator, item);
	assertEquals(translator.i18n("JMenuItem"), item.getText());
	assertEquals(1, o.getListeners().keySet().size());
	item = null;
	System.gc();
	assertEquals(0, o.getListeners().keySet().size());
    }

    @Test
    public void testWeakReferencesJLabel() {
	TranslationUpdater o = new TranslationUpdater();
	JLabel label = new JLabel();
	o.i18n("JLabel", translator, label);
	assertEquals(translator.i18n("JLabel"), label.getText());
	assertEquals(1, o.getListeners().keySet().size());
	label = null;
	System.gc();
	assertEquals(0, o.getListeners().keySet().size());
    }

    @Test
    public void testWeakReferencesJDialog() {
	TranslationUpdater o = new TranslationUpdater();
	JDialog dialog = new JDialog(new JFrame());
	o.i18n("JDialog", translator, dialog);
	assertEquals(translator.i18n("JDialog"), dialog.getTitle());
	assertEquals(1, o.getListeners().keySet().size());
	dialog = null;
	System.gc();
	assertEquals(0, o.getListeners().keySet().size());
    }

    @Test
    public void testWeakReferencesTitleBorder() {
	TranslationUpdater o = new TranslationUpdater();
	TitledBorder titledBorder = new TitledBorder("");
	JComponent component = new JTextField();
	o.i18n("TitledBorder", translator, component, titledBorder);
	assertEquals(translator.i18n("TitledBorder"), titledBorder.getTitle());
	assertEquals(1, o.getListeners().keySet().size());
	component = null;
	System.gc();
	assertEquals(1, o.getListeners().keySet().size());
	titledBorder = null;
	System.gc();
	assertEquals(0, o.getListeners().keySet().size());
    }
}
