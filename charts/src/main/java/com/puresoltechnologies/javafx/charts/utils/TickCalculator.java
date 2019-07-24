package com.puresoltechnologies.javafx.charts.utils;

/**
 * This class contains some utilities for calculating and drawing ticks.
 *
 * @author Rick-Rainer Ludwig
 */
public class TickCalculator {

    public static int calculateStepExponent(double min, double max) {
	return (int) Math.round(Math.log10(max - min) - 1);
    }

    public static double calculateChartMin(double min, int stepExponent) {
	double step = Math.pow(10.0, stepExponent);
	return Math.floor(min / step) * step;
    }

    public static double calculateChartMax(double max, int stepExponent) {
	double step = Math.pow(10.0, stepExponent);
	return Math.ceil(max / step) * step;
    }

}
