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

package com.puresoltechnologies.javafx.rcp.linguist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.puresoltechnologies.javafx.i18n.data.LanguageSet;
import com.puresoltechnologies.javafx.i18n.data.MultiLanguageTranslations;
import com.puresoltechnologies.javafx.i18n.data.SourceLocation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * This panel provides all GUI elements and functionality to edit a single I18n
 * file.
 *
 * @author Rick-Rainer Ludwig
 *
 */
class TranslationPanel extends BorderPane {

    // GUI elements...
    private final ReservoirCellRenderer reservoirCellRenderer = new ReservoirCellRenderer();
    private final Label localeLabel = new Label();
    private final ObservableList<String> sourceList = FXCollections.observableArrayList();
    private final ListView<String> reservoir = new ListView<>();
    private final TextArea source = new TextArea();
    private final TextArea translation = new TextArea();
    private final TextArea location = new TextArea();

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
	VBox panel = new VBox();

	localeLabel.setText(selectedLocale.toString());
	panel.getChildren().add(localeLabel);

	/* Reservoir */
	reservoir.setCellFactory((view) -> new ListCell<>() {

	    @Override
	    public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
		    setText("");
		    setGraphic(null);
		} else {
		    setGraphic(reservoirCellRenderer.getListCellRendererComponent(item, isSelected(), isFocused()));
		}
	    }
	});
	reservoir.getSelectionModel().selectedItemProperty().addListener((oldValue, newValue, component) -> {
	    /*
	     * Reservoir selection was changed. The source field needs to be updated.
	     */
	    changedSource();
	});
	reservoir.setItems(sourceList);
	panel.getChildren().add(new ScrollPane(new TitledPane("Reservoir", reservoir)));

	/* Source */
	source.setEditable(false);
	panel.getChildren().add(new ScrollPane(new TitledPane("Source:", source)));

	/* Translation */
	panel.getChildren().add(new ScrollPane(new TitledPane("Translation:", translation)));

	/* Location */
	location.setEditable(false);
	panel.getChildren().add(new ScrollPane(new TitledPane("Location(s):", location)));

	setCenter(new SplitPane(panel, new Label("<html>Some file<br/>statistics</html>")));
    }

    /**
     * @return the selectedLocale
     */
    public Locale getSelectedLocale() {
	return selectedLocale;
    }

    /**
     * @param selectedLocale the selectedLocale to set
     */
    public void setSelectedLocale(Locale selectedLocale) {
	this.selectedLocale = selectedLocale;
	localeLabel.setText(selectedLocale.toString());
	reservoirCellRenderer.setSelectedLocale(selectedLocale);
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
	if ((!oldSource.isEmpty()) && (!oldTranslation.equals(translation.getText()))) {
	    translations.add(oldSource, selectedLocale, translation.getText());
	    changed = true;
	}
    }

    private void changedSource() {
	setSource(reservoir.getSelectionModel().getSelectedItem());
    }

    private void updateReservoir() {
	if (translations == null) {
	    sourceList.clear();
	    return;
	} else {
	    List<String> listData = new ArrayList<>(translations.getSources());
	    Collections.sort(listData);
	    reservoirCellRenderer.setTranslationsHash(translations);
	    sourceList.addAll(listData);
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

}
