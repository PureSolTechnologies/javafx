package com.puresoltechnologies.javafx.utils.graphics;

import java.util.Arrays;

/**
 * https://en.wikipedia.org/wiki/Matrix_(mathematics)
 * 
 * @author Rick-Rainer Ludwig
 */
public class Matrix2D {

    public static Matrix2D identity() {
	return new Matrix2D(new double[] { 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0 });
    }

    private final double[] elements;

    public Matrix2D(double[] elements) {
	if (elements == null) {
	    throw new IllegalArgumentException("elements must not be null.");
	}
	if (elements.length != 9) {
	    throw new IllegalArgumentException("elements array must have nine elements.");
	}
	this.elements = elements;
    }

    public double get(int i, int j) {
	return elements[i * 3 + j];
    }

    public Matrix2D move(double x, double y) {
	double[] newElements = Arrays.copyOf(elements, elements.length);
	newElements[2] += x;
	newElements[5] += y;
	return new Matrix2D(newElements);
    }

    public Matrix2D rotate(double rad) {
	double[] newElements = Arrays.copyOf(elements, elements.length);
	newElements[0] *= Math.cos(rad);
	newElements[1] *= -Math.sin(rad);
	newElements[3] *= -Math.sin(rad);
	newElements[4] *= Math.cos(rad);
	return new Matrix2D(newElements);
    }

    public Vector2D multiply(Vector2D vector) {
	double[] newElements = new double[] { 0.0, 0.0, 0.0 };
	for (int i = 0; i < 3; i++) {
	    for (int j = 0; j < 3; j++) {
		newElements[i] += vector.get(j) * newElements[i * 3 + j];
	    }
	}
	return new Vector2D(newElements);
    }

    public Matrix2D multiply(Matrix2D vector) {
	double[] newElements = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	for (int i = 0; i < 3; i++) {
	    for (int j = 0; j < 3; j++) {
		newElements[i * 3 + j] += newElements[j * 3 + i] + vector.get(i, j);
	    }
	}
	return new Matrix2D(newElements);
    }

}
