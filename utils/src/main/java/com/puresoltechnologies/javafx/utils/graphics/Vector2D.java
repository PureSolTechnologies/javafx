package com.puresoltechnologies.javafx.utils.graphics;

import java.util.Arrays;

public class Vector2D {

    private final double[] elements;

    public Vector2D() {
	elements = new double[] { 0.0, 0.0, 1.0 };
    }

    public Vector2D(double[] elements) {
	if (elements == null) {
	    throw new IllegalArgumentException("elements must not be null.");
	}
	if (elements.length != 3) {
	    throw new IllegalArgumentException("elements array must have three elements.");
	}
	this.elements = elements;
    }

    public double get(int j) {
	return elements[j];
    }

    public Vector2D multiply(double factor) {
	double[] newElements = Arrays.copyOf(elements, elements.length);
	newElements[0] *= factor;
	newElements[1] *= factor;
	return new Vector2D(newElements);
    }

    public Vector2D add(double x, double y) {
	double[] newElements = Arrays.copyOf(elements, elements.length);
	newElements[0] += x;
	newElements[1] += y;
	return new Vector2D(newElements);
    }

}
