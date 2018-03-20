package com.puresoltechnologies.javafx.extensions.fonts;

import java.io.Serializable;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class FontDefinition implements Serializable {

    private static final long serialVersionUID = 3217549513071937543L;

    public static FontDefinition of(Font font) {
	return new FontDefinition(font.getFamily(), FontWeight.NORMAL, font.getSize(), FontPosture.REGULAR,
		Color.BLACK);
    }

    public static FontDefinition valueOf(String string) {
	String[] parts = string.split(",");
	String family = parts[0];
	double size = Double.parseDouble(parts[1]);
	FontWeight weight = FontWeight.valueOf(parts[2]);
	FontPosture posture = FontPosture.valueOf(parts[3]);
	Color color = Color.valueOf(parts[4]);
	return new FontDefinition(family, weight, size, posture, color);
    }

    private final String family;
    private final FontWeight weight;
    private final double size;
    private final FontPosture posture;
    private final Color color;

    public FontDefinition(String family, FontWeight weight, double size, FontPosture posture, Color color) {
	super();
	this.family = family;
	this.weight = weight;
	this.size = size;
	this.posture = posture;
	this.color = color;
    }

    public String getFamily() {
	return family;
    }

    public FontWeight getWeight() {
	return weight;
    }

    public double getSize() {
	return size;
    }

    public FontPosture getPosture() {
	return posture;
    }

    public Color getColor() {
	return color;
    }

    public Font toFont() {
	return Font.font(family, weight, posture, size);
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder(family);
	builder.append(",");
	builder.append(size);
	builder.append(",");
	builder.append(weight.name());
	builder.append(",");
	builder.append(posture.name());
	builder.append(",");
	builder.append(color);
	return builder.toString();
    }
}
