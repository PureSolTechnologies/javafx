package com.puresoltechnologies.javafx.preferences.dialogs;

import java.util.List;

import com.puresoltechnologies.javafx.preferences.Preferences;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Provides a single page for the {@link PreferencesDialog}.
 *
 * @author Rick-Rainer Ludwig
 */
public interface PreferencesPage {

    /**
     * This is the path used in the {@link PreferencesDialog}'s tree view to link
     * the page to it.
     *
     * @return A {@link List} of {@link String} is returned containing a tree node
     *         name in each element.
     */
    List<String> getPath();

    String getName();

    Image getImage();

    /**
     * Returns a {@link Pane} containing all UI elements for the preferences
     * settings.
     *
     * @return A {@link Pane} is returned which is shown in the
     *         {@link PreferencesDialog}.
     */
    Pane createPane(PreferencesDialog preferencesDialog);

    /**
     * This method resets all settings to 'factory defaults'.
     */
    void reset();

    /**
     * This method is called to load the current preferences from the store to
     * present them in the {@link PreferencesDialog}.
     *
     * @param preferences is a {@link Preferences} object to be used to load the
     *                    preferences from.
     */
    void load(Preferences preferences);

    /**
     * This method is called to save the preferences to the store which are set in
     * the {@link PreferencesDialog}.
     *
     * @param preferences is a {@link Preferences} object to be used to store the
     *                    preferences to.
     */
    void save(Preferences preferences);

}
