package com.puresoltechnologies.javafx.rcp.preferences;

import java.util.Arrays;
import java.util.List;

import com.puresoltechnologies.javafx.preferences.Preferences;
import com.puresoltechnologies.javafx.preferences.dialogs.PreferencesDialog;
import com.puresoltechnologies.javafx.preferences.dialogs.PreferencesPage;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class I18nPreferencesPage implements PreferencesPage {

    @Override
    public String getName() {
	return "Language Settings";
    }

    @Override
    public List<String> getPath() {
	return Arrays.asList("User Interface", "Language Settings");
    }

    @Override
    public Image getImage() {
	return null;
    }

    @Override
    public Pane createPane(PreferencesDialog preferencesDialog) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void reset() {
	// TODO Auto-generated method stub

    }

    @Override
    public void load(Preferences preferences) {
	// TODO Auto-generated method stub

    }

    @Override
    public void save(Preferences preferences) {
	// TODO Auto-generated method stub

    }

}
