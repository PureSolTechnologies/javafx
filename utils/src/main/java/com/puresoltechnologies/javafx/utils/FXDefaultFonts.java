package com.puresoltechnologies.javafx.utils;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * This interface is used to hold some standard fonts.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public interface FXDefaultFonts {

    Font defaultFont = Font.getDefault();
    String defaultFamily = defaultFont.getFamily();
    double defaultSize = defaultFont.getSize();
    Font titleFont = Font.font(defaultFamily, FontWeight.BOLD, defaultSize * 1.3);
    Font boldFont = Font.font(defaultFamily, FontWeight.BOLD, defaultFont.getSize());

}
