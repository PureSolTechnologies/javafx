package com.puresoltechnologies.javafx.extensions.fonts;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.Test;

import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class FontDefinitionTest {

    @Test
    public void testSerialization() throws IOException {
	FontDefinition fd = new FontDefinition("family", FontWeight.EXTRA_BOLD, 12.3, FontPosture.ITALIC, Color.ORCHID);
	try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
	    objectOutputStream.writeObject(fd);
	}
    }

}
