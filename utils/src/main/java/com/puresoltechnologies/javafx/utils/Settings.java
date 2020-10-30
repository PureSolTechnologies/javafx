package com.puresoltechnologies.javafx.utils;

import java.io.File;
import java.io.IOException;

/**
 * This class contains utilities for global settings storage like preferences
 * without workspace or workspace settings.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class Settings {

    private static final String SETTINGS_DIRECTORY_NAME = System.getProperty("user.home") + File.separator + ".javafx";

    /**
     * This method returns the method directory as defined in
     * {@link #SETTINGS_DIRECTORY_NAME}. This method also assures the availability
     * by checking and creating the needed directories.
     *
     * @return A {@link File} is returned pointing to the settings directory.
     * @throws IOException in case of I/O issues.
     */
    public static File getDirectory() throws IOException {
        File settingsDirectory = new File(SETTINGS_DIRECTORY_NAME);
        if (!settingsDirectory.exists()) {
            if (!settingsDirectory.mkdirs()) {
                throw new IOException("Could not create preferences directory '" + SETTINGS_DIRECTORY_NAME + "'.");
            }
        } else {
            if (!settingsDirectory.isDirectory()) {
                throw new IOException("Preferences directory '" + SETTINGS_DIRECTORY_NAME + "' is not a directory.");
            }
        }
        return settingsDirectory;
    }

    /**
     * Private constructor to avoid instantiation.
     */
    private Settings() {
    }
}
