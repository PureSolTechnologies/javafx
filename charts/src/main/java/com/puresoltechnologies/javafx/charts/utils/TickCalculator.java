package com.puresoltechnologies.javafx.charts.utils;

/**
 * This class contains some utilities for calculating and drawing ticks.
 *
 * @author Rick-Rainer Ludwig
 */
public class TickCalculator {

    /**
     * This method calculates the best accuracy for the axis based on min and max
     * value. The result is returned as exponent to base 10. So, there is an
     * accuracy of 0.1 when -1 is returned and an accurracy of 1000 when 3 is
     * returned.
     *
     * @param min is the minimum value to be plotted.
     * @param max is the maximum value to be plotted.
     * @return An integer is returned for accuracy as exponent for base 10.
     */
    public static int calculateAccuracy(double min, double max) {
        if ((max - min) == 0.0) {
            return 0;
        }
        return (int) Math.floor(Math.log10(max - min) - 1);
    }

    /**
     * This calculated the best minimum value for the axis based on the minimum
     * value to be plotted and accuracy. With this minimum, there are no odd numbers
     * shown.
     *
     * @param min              is the minimum value to be plotted.
     * @param accuracyExponent is the accuracy returned by
     *                         {@link #calculateAccuracy(double, double)}.
     * @return Returns the optimal minimum value for the axis as double value.
     */
    public static double calculateChartMin(double min, int accuracyExponent) {
        double step = Math.pow(10.0, accuracyExponent);
        return Math.floor(min / step) * step;
    }

    /**
     * This calculated the best maximum value for the axis based on the maximum
     * value to be plotted and accuracy. With this maximum, there are no odd numbers
     * shown.
     *
     * @param max              is the maximum value to be plotted.
     * @param accuracyExponent is the accuracy returned by
     *                         {@link #calculateAccuracy(double, double)}.
     * @return Returns the optimal maximum value for the axis as double value.
     */

    public static double calculateChartMax(double max, int accuracyExponent) {
        double step = Math.pow(10.0, accuracyExponent);
        return Math.ceil(max / step) * step;
    }

}
