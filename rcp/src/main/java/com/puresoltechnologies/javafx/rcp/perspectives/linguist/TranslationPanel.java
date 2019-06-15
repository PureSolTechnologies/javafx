/****************************************************************************
 *
 *   TranslationPanel.java
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

package com.puresoltechnologies.javafx.rcp.perspectives.linguist;

import java.awt.BorderLayout;
import java.util.Collections;
import java.util.Locale;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.puresoltechnologies.javafx.i18n.TranslationUpdater;
import com.puresoltechnologies.javafx.i18n.Translator;
import com.puresoltechnologies.javafx.i18n.data.LanguageSet;
import com.puresoltechnologies.javafx.i18n.data.MultiLanguageTranslations;
import com.puresoltechnologies.javafx.i18n.data.SourceLocation;

/**
 * This panel provides all GUI elements and functionality to edit a single I18n
 * file.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
class TranslationPanel extends JPanel implements ListSelectionListener,
		CaretListener {

	private static final long serialVersionUID = 1L;

	private static final Translator translator = Translator
			.getTranslator(TranslationPanel.class);

	private final TranslationUpdater translationUpdater = new TranslationUpdater();

	// GUI elements...
	private final ReservoirCellRenderer reservoirCellRenderer = new ReservoirCellRenderer();
	private final JLabel localeLabel = new JLabel();
	private final JList reservoir = new JList();
	private final JTextArea source = new JTextArea();
	private final JTextArea translation = new JTextArea();
	private final JTextArea location = new JTextArea();

	// fields...
	private MultiLanguageTranslations translations = null;

	private boolean changed = false;
	private String oldSource = "";
	private String oldTranslation = "";
	private Locale selectedLocale = Locale.getDefault();

	public TranslationPanel() {
		super();
		initializeDesktop();
	}

	private void initializeDesktop() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(5);
		borderLayout.setVgap(5);
		setLayout(borderLayout);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		localeLabel.setText(selectedLocale.toString());
		panel.add(localeLabel);

		/* Reservoir */
		reservoir.setCellRenderer(reservoirCellRenderer);
		reservoir.setBorder(translationUpdater.i18n("Reservoir", translator,
				reservoir, BorderFactory.createTitledBorder("")));
		reservoir.addListSelectionListener(this);
		panel.add(new JScrollPane(reservoir));

		/* Source */
		source.setEditable(false);
		source.setBorder(translationUpdater.i18n("Source", translator, source,
				BorderFactory.createTitledBorder("")));
		panel.add(new JScrollPane(source));

		/* Translation */
		translation.setBorder(translationUpdater.i18n("Translation:",
				translator, translation, BorderFactory.createTitledBorder("")));
		translation.addCaretListener(this);
		panel.add(new JScrollPane(translation));

		/* Location */
		location.setEditable(false);
		location.setBorder(translationUpdater.i18n("Location(s):", translator,
				location, BorderFactory.createTitledBorder("")));
		panel.add(new JScrollPane(location));

		add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel, new JLabel(
				"<html>Some file<br/>statistics</html>")), BorderLayout.CENTER);
	}

	/**
	 * @return the selectedLocale
	 */
	public Locale getSelectedLocale() {
		return selectedLocale;
	}

	/**
	 * @param selectedLocale
	 *            the selectedLocale to set
	 */
	public void setSelectedLocale(Locale selectedLocale) {
		this.selectedLocale = selectedLocale;
		localeLabel.setText(selectedLocale.toString());
		reservoirCellRenderer.setSelectedLocale(selectedLocale);
		reservoir.repaint();
		updateTranslation();
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public boolean hasChanged() {
		updateTranslationHash();
		return changed;
	}

	public void setTranslations(MultiLanguageTranslations translations) {
		this.translations = translations;
		updateTranslationHash();
		changed = false;
		updateReservoir();
	}

	public MultiLanguageTranslations getTranslations() {
		return translations;
	}

	private void updateTranslationHash() {
		if ((!oldSource.isEmpty())
				&& (!oldTranslation.equals(translation.getText()))) {
			translations.add(oldSource, selectedLocale, translation.getText());
			changed = true;
		}
	}

	private void changedSource() {
		setSource((String) reservoir.getSelectedValue());
	}

	private void updateReservoir() {
		if (translations == null) {
			reservoir.removeAll();
			return;
		} else {
			Vector<String> listData = new Vector<String>(
					translations.getSources());
			Collections.sort(listData);
			reservoirCellRenderer.setTranslationsHash(translations);
			reservoir.setListData(listData);
		}
		setSource(null);
	}

	private void setSource(String text) {
		source.setText(text != null ? text : "");
		updateTranslation();
		updateLocation();
	}

	private void updateTranslation() {
		if ((translations == null) || (source.getText().isEmpty())) {
			translation.setText("");
			translation.setEditable(false);
			return;
		}
		translation.setEditable(true);
		updateTranslationHash();
		oldSource = source.getText();
		if (translations.has(oldSource, selectedLocale)) {
			oldTranslation = translations.get(oldSource, selectedLocale);
		} else {
			oldTranslation = "";
		}
		translation.setText(oldTranslation);
	}

	private void updateLocation() {
		if ((translations == null) || (source.getText().isEmpty())) {
			location.setText("");
			return;
		}
		LanguageSet languageSet = translations.get(source.getText());
		if (languageSet == null) {
			location.setText("");
			return;
		}
		StringBuilder locations = new StringBuilder();
		for (SourceLocation location : languageSet.getLocations()) {
			locations.append(location.toString()).append("\n");
		}
		location.setText(locations.toString());
	}

	public void removeObsoletePhrases() {
		translations.removeSourcesWithoutLocation();
		updateReservoir();
	}

	@Override
	public void valueChanged(ListSelectionEvent o) {
		if (o.getSource() == this.reservoir) {
			/*
			 * Reservoir selection was changed. The source field needs to be
			 * updated.
			 */
			changedSource();
		}
	}

	@Override
	public void caretUpdate(CaretEvent o) {
		if (o.getSource() == translation) {
			/*
			 * The text field was changed, maybe...
			 */
		}
	}

}
