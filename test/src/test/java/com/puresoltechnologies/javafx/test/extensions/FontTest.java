package com.puresoltechnologies.javafx.test.extensions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import javafx.scene.text.Font;

@ExtendWith(ApplicationExtension.class)
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
