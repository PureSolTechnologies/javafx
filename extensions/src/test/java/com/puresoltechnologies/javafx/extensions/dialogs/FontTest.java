package com.puresoltechnologies.javafx.extensions.dialogs;

import org.junit.Test;

import javafx.scene.text.Font;

public class FontTest {

    @Test
    public void testListFontFamilies() {
	Font.getFamilies().forEach(family -> System.out.println("family: " + family));
    }

    @Test
    public void testListFontNames() {
	Font.getFontNames().forEach(fontName -> System.out.println("font name: " + fontName));
    }

    @Test
    public void testListCombinations() {
	Font.getFamilies().forEach(family -> Font.getFontNames(family)
		.forEach(name -> System.out.println("combined: " + family + " / " + name)));
    }

}
