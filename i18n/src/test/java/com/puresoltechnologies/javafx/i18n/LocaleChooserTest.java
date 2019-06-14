package com.puresoltechnologies.javafx.i18n;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Locale;

import org.junit.jupiter.api.Test;

public class LocaleChooserTest {

    @Test
    public void testInstance() {
	assertNotNull(new LocaleChooser());
    }

    @Test
    public void testDefaultValues() {
	LocaleChooser chooser = new LocaleChooser();
	assertEquals(new Locale("ar"), chooser.getSelectedLocale());
    }

    @Test
    public void testSettersAndGetters() {
	LocaleChooser chooser = new LocaleChooser();
	chooser.setSelectedLocale(new Locale("en", "US"));
	assertEquals(new Locale("en", "US"), chooser.getSelectedLocale());
    }

}
